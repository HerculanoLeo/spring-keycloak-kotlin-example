package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.domain.user.User
import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByUserIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserFindByIdUseCase

class UserFindByIdUseCaseImpl(
    private val gateway: UserGateway,
    private val groupFindByUserIdUseCase: GroupFindByUserIdUseCase,
) : UserFindByIdUseCase {
    override fun execute(id: String): User? {
        return gateway.findById(id)?.let {
            val groups = groupFindByUserIdUseCase.execute(id)
            it.copy(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                username = it.lastName,
                email = it.email,
                status = it.status,
                groups = groups
            )
        }
    }
}