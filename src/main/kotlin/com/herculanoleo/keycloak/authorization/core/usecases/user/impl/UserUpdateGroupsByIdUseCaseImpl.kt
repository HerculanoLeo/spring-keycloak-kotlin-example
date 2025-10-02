package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserUpdateGroupsByIdUseCase

class UserUpdateGroupsByIdUseCaseImpl(
    private val userFindByIdUseCase: UserFindByIdUseCase,
    private val gateway: UserGateway,
) : UserUpdateGroupsByIdUseCase {
    override fun execute(id: String, groupIds: List<String>) {
        userFindByIdUseCase.execute(id) ?: throw RuntimeException("User not found")
        gateway.updateGroupsById(id, groupIds)
    }
}