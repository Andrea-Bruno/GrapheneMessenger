package im.molly.unifiedpush.components.settings.app.notifications

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import im.molly.unifiedpush.GrapheneSocketRepository
import im.molly.unifiedpush.model.GrapheneSocket
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.signal.core.util.logging.Log
import org.signal.core.util.toOptional
import org.signal.qr.QrProcessor

/**
 * A collection of functions to help with scanning QR codes for GrapheneSocket.
 */
object GrapheneSocketQrScanRepository {
  private const val TAG = "GrapheneSocketQrScanRepository"

  /**
   * Resolves QR data to a GrapheneSocket link URI, coercing it to a standard set of [QrScanResult]s.
   */
  fun lookupQrLink(data: String): Single<QrScanResult> {
    val uri = Uri.parse(data)
    return when (val grapheneSocket = GrapheneSocket.parseLink(uri)) {
      is GrapheneSocket.AirGapped -> {
        Single.just(QrScanResult.Success(data))
      }

      is GrapheneSocket.WebServer -> {
        checkGrapheneSocketServer(grapheneSocket.url).map { found ->
          if (found) {
            QrScanResult.Success(data)
          } else {
            // TODO add network check
            QrScanResult.NotFound(grapheneSocket.url)
          }
        }.subscribeOn(Schedulers.io())
      }

      else -> Single.just(QrScanResult.InvalidData)
    }
  }

  private fun checkGrapheneSocketServer(url: String): Single<Boolean> {
    return Single
      .fromCallable {
        runCatching {
          GrapheneSocketRepository.discoverGrapheneSocketServer(url.toHttpUrl())
        }.getOrElse { e ->
          Log.e(TAG, "Cannot discover GrapheneSocket", e)
          false
        }
      }.subscribeOn(Schedulers.io())
  }

  fun scanImageUriForQrCode(context: Context, uri: Uri): Single<QrScanResult> {
    val loadBitmap = Glide.with(context)
      .asBitmap()
      .format(DecodeFormat.PREFER_ARGB_8888)
      .load(uri)
      .submit()

    return Single.fromFuture(loadBitmap)
      .map { QrProcessor().getScannedData(it).toOptional() }
      .flatMap {
        if (it.isPresent) {
          lookupQrLink(it.get())
        } else {
          Single.just(QrScanResult.QrNotFound)
        }
      }
      .subscribeOn(Schedulers.io())
  }
}
