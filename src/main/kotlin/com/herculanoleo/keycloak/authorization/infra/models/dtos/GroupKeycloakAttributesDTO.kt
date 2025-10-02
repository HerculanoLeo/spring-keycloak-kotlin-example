package com.herculanoleo.keycloak.authorization.infra.models.dtos

import com.herculanoleo.keycloak.authorization.infra.models.enums.KeycloakAttributesKey

data class GroupKeycloakAttributesDTO(
    val type: List<String>? = null,
    var ownerId: List<String>? = null,
) {
    fun toMap(): Map<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        type?.let { map[KeycloakAttributesKey.TYPE.value] = it }
        ownerId?.let { map[KeycloakAttributesKey.OWNER_ID.value] = it }
        return map
    }
}