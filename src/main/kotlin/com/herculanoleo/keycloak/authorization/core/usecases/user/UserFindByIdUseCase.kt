package com.herculanoleo.keycloak.authorization.core.usecases.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User

interface UserFindByIdUseCase {
    fun execute(id: String): User?
}