package com.herculanoleo.keycloak.authorization.core.usecases.roles.impl

import com.herculanoleo.keycloak.authorization.core.gateway.roles.RolesGateway
import com.herculanoleo.keycloak.authorization.core.usecases.roles.RolesFindAllUseCase

class RolesFindAllUseCaseImpl(
    private val rolesGateway: RolesGateway
) : RolesFindAllUseCase {
    override fun execute() = rolesGateway.findAll()
}