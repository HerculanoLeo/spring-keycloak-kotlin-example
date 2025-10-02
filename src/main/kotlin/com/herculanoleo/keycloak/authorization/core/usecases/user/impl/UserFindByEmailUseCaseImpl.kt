package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByEmailUseCase

class UserFindByEmailUseCaseImpl(private val gateway: UserGateway) : UserFindByEmailUseCase {
    override fun execute(email: String) = gateway.findByEmail(email)
}