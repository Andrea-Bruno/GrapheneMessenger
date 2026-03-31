/*
 * Copyright 2026 Graphene Instant Messenger
 * SPDX-License-Identifier: AGPL-3.0-only
 */

import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Utility object for parsing and manipulating xml resource files.
 */
private object XmlRes {
  fun parseStrings(stringsFile: File): Pair<Document, List<Element>> {
    val doc = parseXmlFile(stringsFile)
    val strings = doc.getElements("string") + doc.getElements("plurals")
    return doc to strings
  }

  fun parseColors(resFile: File): Pair<Document, Map<String, String>> {
    val doc = parseXmlFile(resFile)
    val colors = doc
      .getElements("color")
      .associateBy(
        { it.getAttribute("name") },
        { it.firstChild.nodeValue }
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


/**
 * Updates all translation files by replacing Signal references with Graphene.
 * Only processes strings marked with `grapheneify="true"` attribute.
 */
tasks.register("updateTranslationsAll") {
  group = "Graphene"
  description = "Updates translations in all modules."

  subprojects.forEach { module ->
    val baseStringsFile = module.file("src/main/res/values/strings.xml")
    if (baseStringsFile.exists()) {
      val subtask = module.registerTranslationsTask(baseStringsFile)
      dependsOn(subtask)
    }
  }
}

private fun Project.registerTranslationsTask(baseStringsFile: File): TaskProvider<Task> {
  val baseFileProvider = provider { baseStringsFile }
  val translationFilesProvider = provider {
    fileTree("src/main/res") {
      include("**/values-*/strings.xml")
    }
  }
  val rootDirProvider = provider { rootProject.rootDir }

  val task = tasks.register("updateTranslations") {
    group = "Graphene"
    description = "Updates references to 'Signal' with 'Graphene' in translation files."

    inputs.file(baseFileProvider)
      .withPropertyName("baseStringsFile")
      .withPathSensitivity(PathSensitivity.RELATIVE)

    inputs.files(translationFilesProvider)
      .withPropertyName("translationFiles")
      .withPathSensitivity(PathSensitivity.RELATIVE)

    outputs.files(translationFilesProvider)

    doLast {
      val baseStringsFile = baseFileProvider.get()
      val translationFiles = translationFilesProvider.get()
      val rootDir = rootDirProvider.get()

      // Gather all string names containing "grapheneify" attribute
      val (_, baseStrings) = XmlRes.parseStrings(baseStringsFile)
      val grapheneifyList = baseStrings
        .filter { it.getAttribute("grapheneify") == "true" }
        .map { it.getAttribute("name") }
        .toSet()

      if (grapheneifyList.isNotEmpty() && translationFiles.isEmpty) {
        logger.error("No translation files found in src/main/res/values-*/")
      }

      fun replaceSignalRefs(elem: Element): Boolean {
        val oldContent = elem.textContent
        elem.textContent = elem.textContent
          .replace("signal.org", "graphene.im")
          .replace("Signal", "Graphene")
        return oldContent != elem.textContent
      }

      fun processTranslationFile(stringsFile: File): Boolean {
        val (xmlDoc, translatedStrings) = XmlRes.parseStrings(stringsFile)
        var updated = false

        translatedStrings.forEach { elem ->
          val name = elem.getAttribute("name")
          if (name in grapheneifyList) {
            when (elem.tagName) {
              "string" -> {
                if (replaceSignalRefs(elem)) updated = true
              }

              "plurals" -> {
                val items = elem.getElementsByTagName("item")
                for (i in 0 until items.length) {
                  val item = items.item(i) as Element
                  if (replaceSignalRefs(item)) updated = true
                }
              }
            }
          }
        }

        // Write back the modified translation file only if replacements were made
        if (updated) {
          XmlRes.writeToFile(xmlDoc, stringsFile)
        }
        return updated
      }

      // Iterate through each translation file and perform the replacements
      translationFiles.files.parallelStream().forEach {
        if (processTranslationFile(it)) {
          logger.lifecycle(
            "Updated translations in: " + it.toRelativeString(rootDir)
          )
        }
      }
    }
  }
  return task
}

/**
 * Updates all Signal brand colors to Graphene brand colors across the codebase.
 *
 * Reads color definitions from "core:ui/src/main/res/values/graphene_colors.xml" and replaces
 * all hex color values defined as "stock_*" with their corresponding "graphene_*" counterparts
 * in XML, Kotlin, and Java source files.
 */
tasks.register("updateColors") {
  group = "Graphene"
  description = "Replaces Signal colors with Graphene colors in the app source set."

  val colorsFileProvider = provider {
    project(":core:ui").file("src/main/res/values/graphene_colors.xml")
  }
  val sourceFilesProvider = colorsFileProvider.map { colorsFile ->
    objects.fileCollection().apply {
      subprojects.forEach { module ->
        val srcDir = module.file("src/main")
        from(fileTree(srcDir) {
          include("**/*.xml", "**/*.kt", "**/*.java")
          exclude("res/values*/strings*.xml")
          exclude(colorsFile.relativeTo(srcDir).path)
        })
      }
    }.asFileTree
  }
  val rootDirProvider = provider { rootProject.rootDir }

  inputs.file(colorsFileProvider)
    .withPropertyName("colorsFile")
    .withPathSensitivity(PathSensitivity.RELATIVE)

  outputs.files(sourceFilesProvider)

  doLast {
    val colorsFile = colorsFileProvider.get()
    val sourceFiles = sourceFilesProvider.get()
    val rootDir = rootDirProvider.get()

    val (_, colors) = XmlRes.parseColors(colorsFile)

    // Build color mappings from stock_* to graphene_*
    val colorMappings = colors.keys
      .filter { it.startsWith("stock_") }
      .map { stockName ->
        val grapheneName = stockName.replaceFirst("stock_", "graphene_")
        val stockValue = colors.getValue(stockName).removePrefix("#").uppercase()
        val grapheneValue = colors[grapheneName]?.removePrefix("#")?.uppercase()
          ?: throw GradleException("Missing '$grapheneName' for '$stockName' in '$colorsFile'")
        stockValue to grapheneValue
      }.toSet()

    // Check for circular references (color appears as both source and target)
    val stockToGraphene = colorMappings.groupBy({ it.first }, { it.second })
    val grapheneToStock = colorMappings.groupBy({ it.second }, { it.first })

    val stockConflicts = stockToGraphene.filterValues { it.size > 1 }
    val grapheneConflicts = grapheneToStock.filterValues { it.size > 1 }

    val cycles = grapheneToStock.keys.intersect(stockToGraphene.keys).filterNot { color ->
      color in stockToGraphene[color].orEmpty() && color in grapheneToStock[color].orEmpty()
    }

    if (stockConflicts.isNotEmpty() || grapheneConflicts.isNotEmpty() || cycles.isNotEmpty()) {
      val message = buildString {
        appendLine("Conflict detected! Some colors map to multiple values:")
        stockConflicts.forEach { (color, set) ->
          appendLine("Signal #$color → Graphene: ${set.map { "#$it" }}")
        }
        grapheneConflicts.forEach { (color, set) ->
          appendLine("Graphene #$color ← Signal: ${set.map { "#$it" }}")
        }
        cycles.forEach { color ->
          appendLine("Signal ↔ Graphene: #$color")
        }
      }.trim()
      logger.error(message)
      throw GradleException("Conflicting color mappings found in '$colorsFile'")
    }

    val regexReplacements = colorMappings.map { (stockHex, grapheneHex) ->
      // Groups: (1)prefix (2)alpha (3)hex color
      val regex = """(?i)(0x|#)([0-9A-Fa-f]{2})?($stockHex)\b""".toRegex()
      regex to grapheneHex
    }

    var anyChanges = false

    sourceFiles.files.parallelStream().forEach { file ->
      val content = file.readText()
      var modified = content
      var changes = 0

      regexReplacements.forEach { (regex, newHex) ->
        modified = regex.replace(modified) { match ->
          val (_, prefix, alpha, oldHex) = match.groupValues
          if (!oldHex.equals(newHex, ignoreCase = true)) {
            changes++
            "$prefix$alpha$newHex"
          } else match.value
        }
      }

      if (changes > 0) {
        file.writeText(modified)
        logger.lifecycle(
          "Updated: ${file.toRelativeString(rootDir)}: $changes change(s)"
        )
        anyChanges = true
      }
    }

    logger.lifecycle(
      if (anyChanges) "Finished updating Signal colors to Graphene."
      else "No changes needed. Colors are already updated."
    )
  }
}
