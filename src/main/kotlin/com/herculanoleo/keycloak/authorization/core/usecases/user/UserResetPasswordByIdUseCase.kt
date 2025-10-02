package com.herculanoleo.keycloak.authorization.core.usecases.user

interface UserResetPasswordByIdUseCase {
    fun execute(id: String)
}