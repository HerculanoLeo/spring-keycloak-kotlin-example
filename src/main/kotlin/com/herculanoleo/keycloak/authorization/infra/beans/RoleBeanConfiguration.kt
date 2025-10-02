package com.herculanoleo.keycloak.authorization.infra.beans

import com.herculanoleo.keycloak.authorization.core.gateway.roles.RolesGateway
import com.herculanoleo.keycloak.authorization.core.usecases.roles.RolesFindAllUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.roles.impl.RolesFindAllUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleBeanConfiguration {
    @Bean
    fun rolesFindAllUseCase(rolesGateway: RolesGateway): RolesFindAllUseCase =
        RolesFindAllUseCaseImpl(rolesGateway)
}