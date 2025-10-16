package org.thoughtcrime.securesms.keyvalue

import im.molly.unifiedpush.model.GrapheneSocketDevice
import im.molly.unifiedpush.model.RegistrationStatus
import org.signal.core.util.logging.Log

class UnifiedPushValues(store: KeyValueStore) : SignalStoreValues(store) {

  companion object {
    private val TAG = Log.tag(UnifiedPushValues::class)

    private const val GRAPHENESOCKET_DEVICE_ID = "graphenesocket.deviceId"
    private const val GRAPHENESOCKET_PASSWORD = "graphenesocket.passwd"
    private const val GRAPHENESOCKET_STATUS = "graphenesocket.status"
    private const val GRAPHENESOCKET_AIR_GAPPED = "graphenesocket.airGapped"
    private const val GRAPHENESOCKET_URL = "graphenesocket.url"
    private const val GRAPHENESOCKET_VAPID = "graphenesocket.vapid"
    private const val UNIFIEDPUSH_ENABLED = "up.enabled"
    private const val UNIFIEDPUSH_ENDPOINT = "up.endpoint"
    private const val UNIFIEDPUSH_LAST_RECEIVED_TIME = "up.lastRecvTime"
  }

  override fun onFirstEverAppLaunch() = Unit

  override fun getKeysToIncludeInBackup() = emptyList<String>()

  @get:JvmName("isEnabled")
  var enabled: Boolean by booleanValue(UNIFIEDPUSH_ENABLED, false)

  var device: GrapheneSocketDevice?
    get() {
      return GrapheneSocketDevice(
        deviceId = getInteger(GRAPHENESOCKET_DEVICE_ID, 0),
        password = getString(GRAPHENESOCKET_PASSWORD, null) ?: return null,
      )
    }
    set(device) {
      store.beginWrite()
        .putInteger(GRAPHENESOCKET_DEVICE_ID, device?.deviceId ?: 0)
        .putString(GRAPHENESOCKET_PASSWORD, device?.password)
        .apply()
    }

  fun isGrapheneSocketDevice(deviceId: Int): Boolean =
    deviceId != 0 && getInteger(GRAPHENESOCKET_DEVICE_ID, 0) == deviceId

  var registrationStatus: RegistrationStatus
    get() = RegistrationStatus.fromValue(getInteger(GRAPHENESOCKET_STATUS, -1)) ?: RegistrationStatus.UNKNOWN
    set(status) {
      putInteger(GRAPHENESOCKET_STATUS, status.value)
    }

  var endpoint: String? by stringValue(UNIFIEDPUSH_ENDPOINT, null)

  var airGapped: Boolean by booleanValue(GRAPHENESOCKET_AIR_GAPPED, false)

  var grapheneSocketUrl: String? by stringValue(GRAPHENESOCKET_URL, null)

  var grapheneSocketVapid: String? by stringValue(GRAPHENESOCKET_VAPID, null)

  var lastReceivedTime: Long by longValue(UNIFIEDPUSH_LAST_RECEIVED_TIME, 0)

  val isAvailableOrAirGapped: Boolean
    get() = enabled && registrationStatus == RegistrationStatus.REGISTERED
}
