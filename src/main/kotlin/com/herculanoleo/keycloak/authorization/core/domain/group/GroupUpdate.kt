package com.herculanoleo.keycloak.authorization.core.domain.group

data class GroupUpdate(
    val name: String,
    val roles: List<String> = emptyList(),
)
