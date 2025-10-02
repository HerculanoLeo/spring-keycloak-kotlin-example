package com.herculanoleo.keycloak.authorization.core.usecases.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User
import com.herculanoleo.keycloak.authorization.core.domain.user.UserSearch

interface UserSearchUseCase {
    fun execute(requestEntity: UserSearch): List<User>
}