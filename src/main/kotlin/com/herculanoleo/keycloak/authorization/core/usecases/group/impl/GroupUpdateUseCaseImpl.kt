package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.domain.group.GroupUpdate
import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByNameUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupUpdateRolesUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupUpdateUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.ConflictException
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException

class GroupUpdateUseCaseImpl(
    private val groupFindByIdUseCase: GroupFindByIdUseCase,
    private val groupFindByNameUseCase: GroupFindByNameUseCase,
    private val groupUpdateRolesUseCase: GroupUpdateRolesUseCase,
    private val gateway: GroupGateway
) : GroupUpdateUseCase {
    override fun execute(id: String, requestEntity: GroupUpdate) {
        val group = groupFindByIdUseCase.execute(id) ?: throw NotFoundException("Group not found")

        if (group.name != requestEntity.name) {
            groupFindByNameUseCase.execute(requestEntity.name)?.let {
                throw ConflictException("Group already registered with name ${requestEntity.name}")
            }
        }

        gateway.update(id, requestEntity)

        groupUpdateRolesUseCase.execute(id, requestEntity.roles)
    }
}