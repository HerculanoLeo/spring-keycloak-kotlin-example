package com.herculanoleo.keycloak.common.core.domain.exceptions

import java.time.OffsetDateTime

data class ServerExceptionResponse(
    val message: String?,
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
)
