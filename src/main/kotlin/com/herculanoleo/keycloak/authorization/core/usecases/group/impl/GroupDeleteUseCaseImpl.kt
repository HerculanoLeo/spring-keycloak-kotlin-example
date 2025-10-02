package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupDeleteUseCase

class GroupDeleteUseCaseImpl(
    private val gateway: GroupGateway
) : GroupDeleteUseCase {
    override fun execute(id: String) = gateway.deleteById(id)
}