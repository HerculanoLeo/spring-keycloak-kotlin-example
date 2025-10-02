package com.herculanoleo.keycloak.authorization.core.gateway.user

import com.herculanoleo.keycloak.authorization.core.domain.user.User
import com.herculanoleo.keycloak.authorization.core.domain.user.UserCreate
import com.herculanoleo.keycloak.authorization.core.domain.user.UserSearch
import com.herculanoleo.keycloak.authorization.core.domain.user.UserUpdate

interface UserGateway {
    fun findAll(requestEntity: UserSearch): List<User>
    fun findById(id: String): User?
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
    fun create(requestEntity: UserCreate): User
    fun update(id: String, requestEntity: UserUpdate)
    fun updateGroupsById(id: String, groupIds: List<String>)
    fun activeById(id: String)
    fun inactiveById(id: String)
    fun resetPasswordById(id: String): String?
    fun validatePasswordPolicy(password: String)
}