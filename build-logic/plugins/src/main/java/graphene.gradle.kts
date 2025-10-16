import com.android.build.gradle.AppExtension
import org.jetbrains.kotlin.com.google.gson.Gson
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Utility object for parsing and manipulating xml resource files.
 */
object XmlRes {
  fun parseStrings(stringsFile: File): Pair<Document, List<Element>> {
    val doc = parseXmlFile(stringsFile)
    val strings = doc.getElements("string") + doc.getElements("plurals")
    return doc to strings
  }

  fun parseColors(stringsFile: File): Pair<Document, Map<String, String>> {
    val doc = parseXmlFile(stringsFile)
    val colors = doc.getElements("color").associateBy(
      { it.getAttribute("name") }, { it.firstChild.nodeValue }
    )
    return doc to colors
  }

  fun writeToFile(doc: Document, file: File) {
    val transformer = TransformerFactory.newInstance().newTransformer()
    transformer.transform(DOMSource(doc), StreamResult(file))
  }

  private fun parseXmlFile(file: File): Document {
    val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    return docBuilder.parse(file).apply {
      xmlStandalone = true
    }
  }

  private fun Document.getElements(tagName: String) =
    getElementsByTagName(tagName).let { nodes ->
      (0 until nodes.length).map { nodes.item(it) as Element }
    }
}

val updateTranslationsForGraphene by tasks.registering {
  group = "Graphene"
  description = "Updates references to \"Signal\" with \"Graphene\" in all translation files."

  doLast {
    val englishFile = file("src/main/res/values/strings.xml")
    val (_, englishStrings) = XmlRes.parseStrings(englishFile)

    // Gather all string names containing "grapheneify" attribute
    val grapheneifyList = englishStrings
      .filter { it.getAttribute("grapheneify") == "true" }
      .map { it.getAttribute("name") }
      .toSet()

    // Iterate through each translation file and perform the replacements
    project.fileTree("src/main/res") {
      include("**/values-*/strings.xml")
    }.forEach { translationFile ->
      try {
        val (translationDoc, translatedStrings) = XmlRes.parseStrings(translationFile)
        var modified = false

        translatedStrings.forEach { elem ->
          val name = elem.getAttribute("name")
          if (name in grapheneifyList) {
            when (elem.tagName) {
              "string" -> {
                modified = elem.replaceSignalRefs() or modified
              }

              "plurals" -> {
                val items = elem.getElementsByTagName("item")
                for (i in 0 until items.length) {
                  val item = items.item(i) as Element
                  modified = item.replaceSignalRefs() or modified
                }
              }
            }
          }
        }

        if (modified) {
          // Write back the modified translation file only if replacements were made
          XmlRes.writeToFile(translationDoc, translationFile)
          logger.lifecycle("Updated translations in: ${translationFile.toRelativeString(project.rootDir)}")
        }
      } catch (e: Exception) {
        logger.error("Error processing file: ${translationFile.path}, ${e.message}")
      }
    }
  }
}

private fun Element.replaceSignalRefs(): Boolean {
  val oldContent = textContent
  textContent = textContent
    .replace("Signal", "Graphene")
    .replace("signal.org", "graphene.im")
  return oldContent != textContent
}

val updateColorsForGraphene by tasks.registering {
  group = "custom"
  description = "Replaces Signal colors with Graphene colors in the app source set."

  doLast {
    val appModule = project.rootProject.project(":app")
    val mappingFile = appModule.file("src/main/res/values/graphene_colors.xml")
    val (_, colors) = XmlRes.parseColors(mappingFile)

    val colorMappings = colors
      .filterKeys { it.startsWith("stock_") }
      .map { (signalColorName, signalColor) ->
        val grapheneColor = colors[signalColorName.replaceFirst("stock_", "graphene_")]
          ?: throw GradleException("No 'graphene_*' color found for '$signalColorName' in '$mappingFile'")
        signalColor.uppercase() to grapheneColor.uppercase()
      }
      .toSet()

    val signalToGraphene = colorMappings.groupBy({ it.first }, { it.second })
    val grapheneToSignal = colorMappings.groupBy({ it.second }, { it.first })

    val signalConflicts = signalToGraphene.filterValues { it.size > 1 }
    val grapheneConflicts = grapheneToSignal.filterValues { it.size > 1 }

    val cycles = grapheneToSignal.keys.intersect(signalToGraphene.keys).filterNot { color ->
      color in signalToGraphene[color].orEmpty() && color in grapheneToSignal[color].orEmpty()
    }

    if (signalConflicts.isNotEmpty() || grapheneConflicts.isNotEmpty() || cycles.isNotEmpty()) {
      logger.error("Conflict detected! Some colors map to multiple values:")
      signalConflicts.forEach { (color, set) -> logger.error("Signal $color → Graphene: $set") }
      grapheneConflicts.forEach { (color, set) -> logger.error("Graphene $color ← Signal: $set") }
      cycles.forEach { logger.error("Signal ↔ Graphene: $it") }

      throw GradleException("Conflicting color mappings found in '$mappingFile'")
    }

    val regexReplacements = colorMappings.map { (signalColor, grapheneColor) ->
      val signalHex = signalColor.drop(1)
      val grapheneHex = grapheneColor.drop(1)
      val pattern = """(0x|#)([0-9A-Fa-f]{2})?($signalHex)\b""".toRegex(RegexOption.IGNORE_CASE)
      pattern to grapheneHex
    }

    val sourceFiles = fileTree(project.file("src/main")) {
      include("**/*.xml", "**/*.kt", "**/*.java")
      exclude("**/${mappingFile.name}", "res/values*/strings*.xml")
    }

    var anyChanges = false

    sourceFiles.files.parallelStream().forEach { file ->
      val originalContent = file.readText()
      var modifiedContent = originalContent
      var changesInFile = 0

      regexReplacements.forEach { (regex, newColor) ->
        modifiedContent = regex.replace(modifiedContent) { match ->
          val (_, prefix, alpha, color) = match.groupValues
          if (!color.equals(newColor, ignoreCase = true)) {
            changesInFile++
            "$prefix$alpha$newColor"
          } else match.value
        }
      }

      if (changesInFile > 0) {
        file.writeText(modifiedContent)
        logger.lifecycle("Updated: ${file.toRelativeString(project.rootDir)}: $changesInFile change(s)")
        anyChanges = true
      }
    }

    logger.lifecycle(
      if (anyChanges) "Finished updating Signal colors to Graphene."
      else "No changes needed. Colors are already updated."
    )
  }
}

val version by tasks.registering {
  doLast {
    val android = project.extensions.getByType(AppExtension::class)
    val versionCode = android.defaultConfig.versionCode
    val versionName = android.defaultConfig.versionName

    val versionInfo = mapOf(
      "versionCode" to versionCode,
      "versionName" to versionName
    )

    println(Gson().toJson(versionInfo))
  }
}
