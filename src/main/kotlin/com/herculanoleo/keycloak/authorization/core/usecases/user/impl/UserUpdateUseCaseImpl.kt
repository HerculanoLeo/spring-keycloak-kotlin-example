package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.domain.user.UserUpdate
import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserUpdateGroupsByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserUpdateUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException

class UserUpdateUseCaseImpl(
    private val userFindByIdUseCase: UserFindByIdUseCase,
    private val userUpdateGroupsByIdUseCase: UserUpdateGroupsByIdUseCase,
    private val gateway: UserGateway
) : UserUpdateUseCase {
    override fun execute(
        id: String,
        requestEntity: UserUpdate
    ) = userFindByIdUseCase.execute(id)?.let {
        gateway.update(id, requestEntity)

        if (null != requestEntity.groupIds) {
            userUpdateGroupsByIdUseCase.execute(id, requestEntity.groupIds)
        } else {
            userUpdateGroupsByIdUseCase.execute(id, listOf())
        }
    } ?: throw NotFoundException("User not found")
}