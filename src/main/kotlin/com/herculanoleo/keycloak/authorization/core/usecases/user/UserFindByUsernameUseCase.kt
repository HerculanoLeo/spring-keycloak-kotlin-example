package com.herculanoleo.keycloak.authorization.core.usecases.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User

interface UserFindByUsernameUseCase {
    fun execute(username: String): User?
}