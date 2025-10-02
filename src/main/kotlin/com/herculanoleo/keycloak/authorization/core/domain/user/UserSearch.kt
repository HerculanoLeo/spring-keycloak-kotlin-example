package com.herculanoleo.keycloak.authorization.core.domain.user

data class UserSearch(
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val email: String? = null,
    val status: UserStatus? = null,
)