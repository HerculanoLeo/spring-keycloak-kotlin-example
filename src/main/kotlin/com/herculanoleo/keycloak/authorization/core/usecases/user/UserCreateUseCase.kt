package com.herculanoleo.keycloak.authorization.core.usecases.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User
import com.herculanoleo.keycloak.authorization.core.domain.user.UserCreate

interface UserCreateUseCase {
    fun execute(requestEntity: UserCreate): User
}