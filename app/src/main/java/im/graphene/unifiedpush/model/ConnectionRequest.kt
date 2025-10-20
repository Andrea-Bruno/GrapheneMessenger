package im.graphene.unifiedpush.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ConnectionRequest(
  @JsonProperty("uuid") val uuid: String,
  @JsonProperty("device_id") val deviceId: Int,
  @JsonProperty("password") val password: String,
  @JsonProperty("endpoint") val endpoint: String,
  @JsonProperty("ping") val ping: Boolean,
)

data class Response(
  @JsonProperty("graphenesocket") val grapheneSocket: ResponseGrapheneSocket,
)

data class ResponseGrapheneSocket(
  @JsonProperty("version") val version: String,
  @JsonProperty("status") val status: ConnectionResult?,
)

enum class ConnectionResult(private val formatted: String) {
  OK("ok"),
  FORBIDDEN("forbidden"),
  INVALID_UUID("invalid_uuid"),
  INVALID_ENDPOINT("invalid_endpoint"),
  INTERNAL_ERROR("internal_error");

  override fun toString(): String = formatted
}
