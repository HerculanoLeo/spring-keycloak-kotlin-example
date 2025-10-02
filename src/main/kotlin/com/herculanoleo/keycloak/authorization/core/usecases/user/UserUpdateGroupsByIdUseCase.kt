package com.herculanoleo.keycloak.authorization.core.usecases.user

interface UserUpdateGroupsByIdUseCase {
    fun execute(id: String, groupIds: List<String>)
}