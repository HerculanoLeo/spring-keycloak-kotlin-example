package com.herculanoleo.keycloak.authorization.core.gateway.roles

import com.herculanoleo.keycloak.authorization.core.domain.roles.Role

interface RolesGateway {
    fun findAll(): List<Role>
}