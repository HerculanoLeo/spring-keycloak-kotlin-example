package com.herculanoleo.keycloak.authorization.core.usecases.user

import com.herculanoleo.keycloak.authorization.core.domain.user.UserUpdate

interface UserUpdateUseCase {
    fun execute(id: String, requestEntity: UserUpdate)
}