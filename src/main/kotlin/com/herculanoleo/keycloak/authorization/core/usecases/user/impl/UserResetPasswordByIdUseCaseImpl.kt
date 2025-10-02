package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserResetPasswordByIdUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException

class UserResetPasswordByIdUseCaseImpl(
    private val userFindByIdUseCase: UserFindByIdUseCase,
    private val gateway: UserGateway
) : UserResetPasswordByIdUseCase {
    override fun execute(id: String) {
        userFindByIdUseCase.execute(id) ?: throw NotFoundException("User not found")
        gateway.resetPasswordById(id)?.let {
        }
    }
}