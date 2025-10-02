package com.herculanoleo.keycloak.authorization.core.gateway.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupRegister
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupSearch
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupUpdate
import com.herculanoleo.keycloak.authorization.core.domain.roles.Role

interface GroupGateway {
    fun findAll(requestEntity: GroupSearch): List<Group>
    fun findByUserId(userId: String): List<Group>?
    fun findById(id: String): Group?
    fun deleteById(id: String)
    fun findByName(name: String): Group?
    fun register(requestEntity: GroupRegister): Group
    fun update(id: String, requestEntity: GroupUpdate)
    fun updateRolesById(id: String, newRoles: List<Role>)
}