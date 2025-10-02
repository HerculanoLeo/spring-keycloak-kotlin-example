package com.herculanoleo.keycloak.authorization.infra.mapper.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User
import com.herculanoleo.keycloak.authorization.core.domain.user.UserStatus
import com.herculanoleo.keycloak.authorization.infra.models.dtos.UserDTO
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun domain(user: UserRepresentation): User = User(
        id = user.id,
        firstName = user.firstName ?: "",
        lastName = user.lastName ?: "",
        username = user.username ?: "",
        email = user.email ?: "",
        status = if (user.isEnabled) UserStatus.ACTIVE else UserStatus.INACTIVE,
        groups = null
    )

    fun dto(user: User): UserDTO = UserDTO(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        username = user.username,
        email = user.email,
        status = user.status,
        groups = user.groups,
    )

}