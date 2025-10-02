package com.herculanoleo.keycloak.authorization.core.usecases.user

interface UserActiveByIdUseCase {
    fun execute(id: String)
}