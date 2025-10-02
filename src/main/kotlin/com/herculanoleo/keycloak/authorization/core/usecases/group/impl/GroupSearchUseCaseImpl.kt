package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.domain.group.GroupSearch
import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupSearchUseCase

class GroupSearchUseCaseImpl(
    private val gateway: GroupGateway
) : GroupSearchUseCase {
    override fun execute(requestEntity: GroupSearch) = gateway.findAll(requestEntity)
}