package com.herculanoleo.keycloak.authorization.core.domain.user

data class UserUpdate(
    val firstName: String,
    val lastName: String,
    val groupIds: List<String>?,
)
