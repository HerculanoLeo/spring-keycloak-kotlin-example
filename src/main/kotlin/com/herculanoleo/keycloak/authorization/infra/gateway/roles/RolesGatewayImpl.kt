package com.herculanoleo.keycloak.authorization.infra.gateway.roles

import com.herculanoleo.keycloak.authorization.core.domain.roles.Role
import com.herculanoleo.keycloak.authorization.core.gateway.roles.RolesGateway
import com.herculanoleo.keycloak.authorization.infra.mapper.roles.RolesMapper
import org.keycloak.admin.client.resource.RolesResource
import org.springframework.stereotype.Service

@Service
class RolesGatewayImpl(
    private val rolesResource: RolesResource,
    private val rolesMapper: RolesMapper
) : RolesGateway {
    override fun findAll(): List<Role> {
        return rolesResource.list().map { rolesMapper.toDomain(it) }
    }
}