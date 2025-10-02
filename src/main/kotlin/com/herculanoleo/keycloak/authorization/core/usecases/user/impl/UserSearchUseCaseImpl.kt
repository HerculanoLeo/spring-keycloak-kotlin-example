package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.domain.user.UserSearch
import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserSearchUseCase

class UserSearchUseCaseImpl(
    private val gateway: UserGateway
) : UserSearchUseCase {
    override fun execute(requestEntity: UserSearch) = gateway.findAll(requestEntity)
}