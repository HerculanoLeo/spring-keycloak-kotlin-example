package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByUsernameUseCase

class UserFindByUsernameUseCaseImpl(private val gateway: UserGateway) : UserFindByUsernameUseCase {
    override fun execute(username: String) = gateway.findByUsername(username)
}