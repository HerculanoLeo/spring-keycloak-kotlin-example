package com.herculanoleo.keycloak.authorization.core.usecases.user

interface UserInactiveByIdUseCase {
    fun execute(id: String)
}