package com.herculanoleo.keycloak.authorization.infra.models.dtos

data class GroupKeycloakDTO(
    var id: String,
    var name: String,
    var path: String,
    var subGroupCount: Int? = null,
    var subGroups: List<GroupKeycloakDTO>? = null,
    var attributes: GroupKeycloakDTO? = null,
    var realmRoles: List<String> = listOf(),
)
