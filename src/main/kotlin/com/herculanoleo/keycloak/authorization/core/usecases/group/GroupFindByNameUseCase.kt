package com.herculanoleo.keycloak.authorization.core.usecases.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group

interface GroupFindByNameUseCase {
    fun execute(name: String): Group?
}