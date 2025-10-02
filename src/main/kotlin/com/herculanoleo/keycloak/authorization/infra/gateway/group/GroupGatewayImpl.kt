package com.herculanoleo.keycloak.authorization.infra.gateway.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupRegister
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupSearch
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupUpdate
import com.herculanoleo.keycloak.authorization.core.domain.roles.Role
import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.infra.mapper.group.GroupMapper
import com.herculanoleo.keycloak.common.core.extensions.lastPath
import jakarta.ws.rs.NotFoundException
import org.apache.commons.lang3.Strings
import org.keycloak.admin.client.resource.GroupsResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.GroupRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Service

@Service
class GroupGatewayImpl(
    private val groupsResource: GroupsResource,
    private val usersResource: UsersResource,
    private val groupMapper: GroupMapper
) : GroupGateway {
    override fun findAll(requestEntity: GroupSearch): List<Group> {
        val query = StringBuilder()

        val groups = groupsResource.query(query.toString(), true, null, null, false)

        val result = requestEntity.name?.let {
            groups.filter { group -> Strings.CI.contains(group.name, it) }
        } ?: groups

        return result.map { groupMapper.domain(it) }
    }

    override fun findById(id: String) = try {
        val groupResource = groupsResource.group(id)
        val groupRepresentation = groupResource.toRepresentation()
        groupRepresentation.realmRoles = groupResource.roles().realmLevel().listAll().map { it.name }
        groupMapper.domain(groupResource.toRepresentation())
    } catch (_: NotFoundException) {
        null
    }

    override fun findByUserId(userId: String) = try {
        val groups = usersResource.get(userId).groups(null, false)
        groups?.map { groupMapper.domain(it) }
    } catch (_: NotFoundException) {
        null
    }

    override fun deleteById(id: String) = groupsResource.group(id).remove()

    override fun findByName(name: String) =
        groupsResource.groups(name, 0, 1).firstOrNull()?.let { groupMapper.domain(it) }

    override fun register(requestEntity: GroupRegister): Group {
        val representation = GroupRepresentation()

        representation.name = requestEntity.name

        return groupsResource.add(representation).use {
            val id = it.location.lastPath()
            groupsResource.group(id).toRepresentation().let { group -> groupMapper.domain(group) }
        }
    }

    override fun update(
        id: String,
        requestEntity: GroupUpdate
    ) {
        val groupResource = groupsResource.group(id)

        val groupRepresentation = groupResource.toRepresentation()
        groupRepresentation.name = requestEntity.name

        groupResource.update(groupRepresentation)
    }

    override fun updateRolesById(id: String, newRoles: List<Role>) {
        val rolesToRemove = groupsResource.group(id).roles().realmLevel().listAll()
        groupsResource.group(id).roles().realmLevel().remove(rolesToRemove)
        groupsResource.group(id).roles().realmLevel().add(newRoles.map {
            val rep = RoleRepresentation()
            rep.id = it.id
            rep.name = it.name
            rep
        })
    }
}