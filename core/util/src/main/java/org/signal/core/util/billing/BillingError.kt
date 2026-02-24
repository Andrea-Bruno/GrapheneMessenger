/*
 * Copyright 2024 Graphene Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.core.util.billing

class BillingError(
  val billingResponseCode: Int
) : Exception("$billingResponseCode")
