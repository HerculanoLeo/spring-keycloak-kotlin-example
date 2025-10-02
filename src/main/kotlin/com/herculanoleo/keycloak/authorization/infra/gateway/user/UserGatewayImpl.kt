package com.herculanoleo.keycloak.authorization.infra.gateway.user

import com.herculanoleo.keycloak.authorization.core.domain.user.*
import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.infra.beans.PasswordConstraintAttributes
import com.herculanoleo.keycloak.authorization.infra.mapper.user.UserMapper
import com.herculanoleo.keycloak.authorization.infra.models.enums.KeycloakRequiredAction
import com.herculanoleo.keycloak.common.core.domain.exceptions.BadRequestException
import com.herculanoleo.keycloak.common.core.domain.exceptions.ConflictException
import com.herculanoleo.keycloak.common.core.extensions.lastPath
import jakarta.ws.rs.NotFoundException
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.springframework.stereotype.Service

@Service
class UserGatewayImpl(
    private val usersResource: UsersResource,
    private val mapper: UserMapper,
    private val passwordConstraintAttributes: PasswordConstraintAttributes,
    private val passwordValidator: PasswordValidator
) : UserGateway {

    override fun findAll(requestEntity: UserSearch): List<User> {
        val users = usersResource.search(
            requestEntity.username,
            requestEntity.firstName,
            requestEntity.lastName,
            requestEntity.email,
            null,
            null,
            requestEntity.status?.let { it == UserStatus.ACTIVE },
            null,
            false
        )

        return users.map { mapper.domain(it) }
    }

    override fun findById(id: String): User? {
        try {
            val user = usersResource.get(id).toRepresentation()
            return mapper.domain(user)
        } catch (_: NotFoundException) {
            return null
        }
    }

    override fun findByEmail(email: String): User? {
        val user = usersResource.searchByEmail(email, true).firstOrNull()
        return user?.let { mapper.domain(it) }
    }

    override fun findByUsername(username: String): User? {
        val user = usersResource.search(username).firstOrNull()
        return user?.let { mapper.domain(it) }
    }

    override fun create(requestEntity: UserCreate): User {
        val representation = UserRepresentation()
        representation.username = requestEntity.username
        representation.email = requestEntity.email
        representation.firstName = requestEntity.firstName
        representation.lastName = requestEntity.lastName
        representation.isEnabled = true

        if (requestEntity.password?.isNotBlank() == true) {
            val credentialRepresentation = CredentialRepresentation()
            credentialRepresentation.type = CredentialRepresentation.PASSWORD
            credentialRepresentation.value = requestEntity.password
            representation.credentials = listOf(credentialRepresentation)
        }

        usersResource.create(representation).use {
            val keycloakId = it.location.lastPath()

            val requiredActions = mutableListOf(KeycloakRequiredAction.VERIFY_EMAIL.name)

            if (requestEntity.password.isNullOrBlank()) {
                requiredActions.add(KeycloakRequiredAction.UPDATE_PASSWORD.name)
            }

            usersResource.get(keycloakId).executeActionsEmail(requiredActions)

            return mapper.domain(this.usersResource.get(keycloakId).toRepresentation())
        }
    }

    override fun update(
        id: String,
        requestEntity: UserUpdate
    ) {
        val userResource = this.usersResource.get(id)
        val userRepresentation = userResource.toRepresentation()
        userRepresentation.firstName = requestEntity.firstName
        userRepresentation.lastName = requestEntity.lastName
        userResource.update(userRepresentation)
    }

    override fun updateGroupsById(id: String, groupIds: List<String>) {
        val userResource = this.usersResource.get(id)
        val actualGroups = userResource.groups()

        actualGroups.forEach { actual ->
            if (groupIds.none { groupId -> actual.id == groupId }) {
                userResource.leaveGroup(actual.id)
            }
        }

        groupIds.forEach { groupId ->
            if (actualGroups.none { actual -> groupId == actual.id }) {
                userResource.joinGroup(groupId)
            }
        }
    }

    override fun activeById(id: String) {
        val userResource = this.usersResource.get(id)
        val userRepresentation = userResource.toRepresentation()

        if (userRepresentation.isEnabled) {
            throw ConflictException("User already active")
        }

        userRepresentation.isEnabled = false

        userResource.update(userRepresentation)
    }

    override fun inactiveById(id: String) {
        val userResource = this.usersResource.get(id)
        val userRepresentation = userResource.toRepresentation()

        if (!userRepresentation.isEnabled) {
            throw ConflictException("User already inactive")
        }

        userRepresentation.isEnabled = true
        userResource.update(userRepresentation)
    }

    override fun resetPasswordById(id: String): String? {
        usersResource.get(id).executeActionsEmail(
            listOf(
                KeycloakRequiredAction.UPDATE_PASSWORD.name
            )
        )
        return null
    }

    override fun validatePasswordPolicy(password: String) {
        val result = passwordValidator.validate(PasswordData(password))

        if (!result.isValid) {
            throw BadRequestException(
                """
                    Senha não corresponde aos requisitos mínimos de segurança.
                    A senha deve conter ao menos ${passwordConstraintAttributes.lower} letra(s) maiúscula(s),
                    ${passwordConstraintAttributes.upper} minúscula(s), 
                    ${passwordConstraintAttributes.lower} número(s) e
                    ${passwordConstraintAttributes.special} caractere(s) especial
                """.trimIndent()
            )
        }
    }

}