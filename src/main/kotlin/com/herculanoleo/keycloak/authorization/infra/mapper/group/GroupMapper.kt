package com.herculanoleo.keycloak.authorization.infra.mapper.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.stereotype.Component

@Component
class GroupMapper {

    fun domain(group: GroupRepresentation) = Group(
        id = group.id,
        name = group.name,
        roles = group.realmRoles ?: emptyList(),
    )

}