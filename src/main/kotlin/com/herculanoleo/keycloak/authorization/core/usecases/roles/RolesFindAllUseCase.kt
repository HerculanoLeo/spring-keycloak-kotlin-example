package com.herculanoleo.keycloak.authorization.core.usecases.roles

import com.herculanoleo.keycloak.authorization.core.domain.roles.Role

interface RolesFindAllUseCase {
    fun execute(): List<Role>
}