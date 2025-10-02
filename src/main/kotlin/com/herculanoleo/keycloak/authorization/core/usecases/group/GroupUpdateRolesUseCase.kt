package com.herculanoleo.keycloak.authorization.core.usecases.group

interface GroupUpdateRolesUseCase {
    fun execute(id: String, roleNames: List<String>)
}