package com.herculanoleo.keycloak.authorization.infra.models.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import com.herculanoleo.keycloak.authorization.core.domain.user.UserStatus

data class UserDTO(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val status: UserStatus,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val groups: List<Group>?,
)
