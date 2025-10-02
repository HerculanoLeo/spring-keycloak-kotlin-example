package com.herculanoleo.keycloak.authorization.core.usecases.user

interface UserValidatePasswordPolicyUseCase {
    fun execute(password: String)
}