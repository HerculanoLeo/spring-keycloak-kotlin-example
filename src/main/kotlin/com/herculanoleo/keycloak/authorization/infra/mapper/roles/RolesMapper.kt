package com.herculanoleo.keycloak.authorization.infra.mapper.roles

import com.herculanoleo.keycloak.authorization.core.domain.roles.Role
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Component

@Component
class RolesMapper {

    fun toDomain(representation: RoleRepresentation): Role {
        return Role(
            id = representation.id,
            name = representation.name,
            description = representation.description,
        )
    }

}