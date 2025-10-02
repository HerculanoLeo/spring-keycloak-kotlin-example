package com.herculanoleo.keycloak.authorization.core.usecases.group

interface GroupDeleteUseCase {
    fun execute(id: String)
}