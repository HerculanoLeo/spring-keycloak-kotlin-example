package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByNameUseCase

class GroupFindByNameUseCaseImpl(
    private val gateway: GroupGateway
) : GroupFindByNameUseCase {
    override fun execute(name: String) = gateway.findByName(name)
}