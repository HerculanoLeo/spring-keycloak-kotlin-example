package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserInactiveByIdUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException

class InactiveByIdUseCaseImplUser(
    private val userFindByIdUseCase: UserFindByIdUseCase,
    private val gateway: UserGateway
) : UserInactiveByIdUseCase {
    override fun execute(id: String) =
        userFindByIdUseCase.execute(id)?.let { gateway.inactiveById(id) } ?: throw NotFoundException("User not found")
}