package com.herculanoleo.keycloak.authorization.core.usecases.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group

interface GroupFindByIdUseCase {
    fun execute(id: String): Group?
}