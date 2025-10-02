package com.herculanoleo.keycloak.authorization.core.domain.group

data class GroupRegister(
    val name: String,
    val roles: List<String> = emptyList(),
)
