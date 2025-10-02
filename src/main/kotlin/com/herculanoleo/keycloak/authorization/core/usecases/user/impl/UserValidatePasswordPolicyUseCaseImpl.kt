package com.herculanoleo.keycloak.authorization.core.usecases.user.impl

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.user.UserValidatePasswordPolicyUseCase

class UserValidatePasswordPolicyUseCaseImpl(private val gateway: UserGateway) : UserValidatePasswordPolicyUseCase {
    override fun execute(password: String) = gateway.validatePasswordPolicy(password)
}