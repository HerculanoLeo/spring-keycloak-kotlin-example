package com.herculanoleo.keycloak.authorization.core.domain.group

data class Group(
    val id: String,
    val name: String,
    val roles: List<String> = emptyList(),
)
