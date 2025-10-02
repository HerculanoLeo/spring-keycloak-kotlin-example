package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserActiveByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByIdUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException

class UserActiveByIdUseCaseImpl(
    private val userFindByIdUseCase: UserFindByIdUseCase,
    private val gateway: UserGateway
) : UserActiveByIdUseCase {
    override fun execute(id: String) =
        userFindByIdUseCase.execute(id)?.let { gateway.activeById(id) } ?: throw NotFoundException("User not found")
}