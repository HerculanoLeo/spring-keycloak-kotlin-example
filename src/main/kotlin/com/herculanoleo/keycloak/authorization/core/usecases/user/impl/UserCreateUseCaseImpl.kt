package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.domain.user.User
import com.herculanoleo.keycloak.authorization.core.domain.user.UserCreate
import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.*
import com.herculanoleo.keycloak.common.core.domain.exceptions.ConflictException

class UserCreateUseCaseImpl(
    private val userFindByEmailUseCase: UserFindByEmailUseCase,
    private val userFindByUsernameUseCase: UserFindByUsernameUseCase,
    private val userValidatePasswordPolicyUseCase: UserValidatePasswordPolicyUseCase,
    private val userUpdateGroupsByIdUseCase: UserUpdateGroupsByIdUseCase,
    private val gateway: UserGateway
) : UserCreateUseCase {
    override fun execute(requestEntity: UserCreate): User {
        userFindByEmailUseCase.execute(requestEntity.email)?.let { throw ConflictException("Email already registered") }

        userFindByUsernameUseCase.execute(requestEntity.username)
            ?.let { throw ConflictException("Username already registered") }

        requestEntity.password?.let {
            userValidatePasswordPolicyUseCase.execute(it)
        }

        val user = gateway.create(requestEntity)

        requestEntity.groupIds?.let { userUpdateGroupsByIdUseCase.execute(user.id, it) }

        return user
    }
}