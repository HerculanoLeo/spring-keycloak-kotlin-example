package com.herculanoleo.keycloak.authorization.core.domain.user

import com.herculanoleo.keycloak.authorization.core.domain.group.Group

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val status: UserStatus,
    val groups: List<Group>?,
)
