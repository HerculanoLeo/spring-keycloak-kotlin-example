package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupRegister
import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupCreateUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByNameUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupUpdateRolesUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.ConflictException
import jakarta.ws.rs.NotFoundException

class GroupCreateUseCaseImpl(
    private val groupFindByNameUseCase: GroupFindByNameUseCase,
    private val groupUpdateRolesUseCase: GroupUpdateRolesUseCase,
    private val groupFindByIdUseCase: GroupFindByIdUseCase,
    private val gateway: GroupGateway
) : GroupCreateUseCase {
    override fun execute(requestEntity: GroupRegister): Group {
        groupFindByNameUseCase.execute(requestEntity.name)?.let {
            throw ConflictException("Group already registered with name ${requestEntity.name}")
        }

        val group = gateway.register(requestEntity)

        groupUpdateRolesUseCase.execute(group.id, requestEntity.roles)

        return groupFindByIdUseCase.execute(group.id) ?: throw NotFoundException("Group not found")
    }
}