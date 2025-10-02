package com.herculanoleo.keycloak.authorization.core.domain.user

data class UserCreate(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String?,
    val groupIds: List<String>?,
)
