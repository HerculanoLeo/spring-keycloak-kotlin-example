package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByIdUseCase

class GroupFindByIdUseCaseImpl(
    private val gateway: GroupGateway
) : GroupFindByIdUseCase {
    override fun execute(id: String) = gateway.findById(id)
}