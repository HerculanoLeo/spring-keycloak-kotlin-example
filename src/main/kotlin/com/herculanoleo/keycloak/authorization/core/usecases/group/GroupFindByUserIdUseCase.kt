package com.herculanoleo.keycloak.authorization.core.usecases.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group

interface GroupFindByUserIdUseCase {
    fun execute(userId: String): List<Group>?
}