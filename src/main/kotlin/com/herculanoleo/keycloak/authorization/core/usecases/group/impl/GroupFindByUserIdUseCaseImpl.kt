package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByUserIdUseCase

class GroupFindByUserIdUseCaseImpl(
    private val gateway: GroupGateway
) : GroupFindByUserIdUseCase {
    override fun execute(userId: String) = gateway.findByUserId(userId)
}