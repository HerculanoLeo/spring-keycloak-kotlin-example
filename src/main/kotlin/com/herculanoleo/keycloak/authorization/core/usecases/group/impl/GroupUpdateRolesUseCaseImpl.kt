package com.herculanoleo.keycloak.authorization.core.usecases.group.impl

import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupUpdateRolesUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.roles.RolesFindAllUseCase
import com.herculanoleo.keycloak.common.core.domain.exceptions.BadRequestException
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException

class GroupUpdateRolesUseCaseImpl(
    private val findByIdUseCase: GroupFindByIdUseCase,
    private val rolesFindAllUseCase: RolesFindAllUseCase,
    private val gateway: GroupGateway,
) : GroupUpdateRolesUseCase {
    override fun execute(id: String, roleNames: List<String>) {
        findByIdUseCase.execute(id)?.let {
            val roles = rolesFindAllUseCase.execute()

            roleNames.forEach { roleName ->
                roles.find { it.name == roleName } ?: throw BadRequestException("role $roleName not found")
            }

            val newRoles = roles.filter { role -> roleNames.contains(role.name) }

            gateway.updateRolesById(id, newRoles)
        } ?: throw NotFoundException("group with id $id not found")
    }
}