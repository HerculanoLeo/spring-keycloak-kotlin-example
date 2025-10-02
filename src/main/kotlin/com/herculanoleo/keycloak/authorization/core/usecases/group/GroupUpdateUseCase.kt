package com.herculanoleo.keycloak.authorization.core.usecases.group

import com.herculanoleo.keycloak.authorization.core.domain.group.GroupUpdate

interface GroupUpdateUseCase {
    fun execute(id: String, requestEntity: GroupUpdate)
}