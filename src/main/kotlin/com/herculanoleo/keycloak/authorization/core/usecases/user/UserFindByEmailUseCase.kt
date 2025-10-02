package com.herculanoleo.keycloak.authorization.core.usecases.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User

interface UserFindByEmailUseCase {
    fun execute(email: String): User?
}