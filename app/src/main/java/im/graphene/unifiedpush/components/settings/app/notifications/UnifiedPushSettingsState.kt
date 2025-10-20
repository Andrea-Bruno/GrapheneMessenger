package im.graphene.unifiedpush.components.settings.app.notifications

import im.graphene.unifiedpush.model.GrapheneSocketDevice
import im.graphene.unifiedpush.model.RegistrationStatus

data class Distributor(
  val applicationId: String,
  val name: String,
)

data class UnifiedPushSettingsState(
  val airGapped: Boolean,
  val device: GrapheneSocketDevice?,
  val aci: String?,
  val registrationStatus: RegistrationStatus,
  val distributors: List<Distributor>,
  val selected: Int,
  val selectedNotAck: Boolean,
  val endpoint: String?,
  val grapheneSocketUrl: String?,
)
