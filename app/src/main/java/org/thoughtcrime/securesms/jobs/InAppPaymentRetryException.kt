/*
 * Copyright 2024 Graphene Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.thoughtcrime.securesms.jobs

/**
 * Denotes that a thrown exception can be retried
 */
class InAppPaymentRetryException(
  cause: Throwable? = null
) : Exception(cause)
