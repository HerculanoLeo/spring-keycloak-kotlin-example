package com.herculanoleo.keycloak.authorization.infra.beans

import com.herculanoleo.keycloak.authorization.core.gateway.user.UserGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.GroupFindByUserIdUseCase
import com.herculanoleo.keycloak.authorization.core.usecases.user.*
import com.herculanoleo.keycloak.authorization.core.usecases.user.impl.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserBeanConfiguration {

    @Bean
    fun searchUserUseCase(gateway: UserGateway): UserSearchUseCase = UserSearchUseCaseImpl(gateway)

    @Bean
    fun findByIdUserUseCase(
        gateway: UserGateway,
        groupFindByUserIdUseCase: GroupFindByUserIdUseCase,
    ): UserFindByIdUseCase = UserFindByIdUseCaseImpl(gateway, groupFindByUserIdUseCase)

    @Bean
    fun findByEmailUserUseCase(gateway: UserGateway): UserFindByEmailUseCase = UserFindByEmailUseCaseImpl(gateway)

    @Bean
    fun findByUsernameUserUseCase(gateway: UserGateway): UserFindByUsernameUseCase =
        UserFindByUsernameUseCaseImpl(gateway)

    @Bean
    fun validatePasswordPolicyUseCase(gateway: UserGateway): UserValidatePasswordPolicyUseCase =
        UserValidatePasswordPolicyUseCaseImpl(gateway)

    @Bean
    fun createUserUseCase(
        userFindByEmailUseCase: UserFindByEmailUseCase,
        userFindByUsernameUseCase: UserFindByUsernameUseCase,
        userValidatePasswordPolicyUseCase: UserValidatePasswordPolicyUseCase,
        userUpdateGroupsByIdUseCase: UserUpdateGroupsByIdUseCase,
        gateway: UserGateway
    ): UserCreateUseCase = UserCreateUseCaseImpl(
        userFindByEmailUseCase,
        userFindByUsernameUseCase,
        userValidatePasswordPolicyUseCase,
        userUpdateGroupsByIdUseCase,
        gateway
    )

    @Bean
    fun updateUserUseCase(
        userFindByIdUseCase: UserFindByIdUseCase,
        userUpdateGroupsByIdUseCase: UserUpdateGroupsByIdUseCase,
        gateway: UserGateway
    ): UserUpdateUseCase = UserUpdateUseCaseImpl(
        userFindByIdUseCase,
        userUpdateGroupsByIdUseCase,
        gateway
    )

    @Bean
    fun updateGroupsByIdUserUseCase(
        userFindByIdUseCase: UserFindByIdUseCase,
        gateway: UserGateway
    ): UserUpdateGroupsByIdUseCase =
        UserUpdateGroupsByIdUseCaseImpl(userFindByIdUseCase, gateway)

    @Bean
    fun activeByIdUserUseCase(userFindByIdUseCase: UserFindByIdUseCase, gateway: UserGateway): UserActiveByIdUseCase =
        UserActiveByIdUseCaseImpl(userFindByIdUseCase, gateway)

    @Bean
    fun inactiveByIdUserUseCase(
        userFindByIdUseCase: UserFindByIdUseCase,
        gateway: UserGateway
    ): UserInactiveByIdUseCase =
        InactiveByIdUseCaseImplUser(userFindByIdUseCase, gateway)

    @Bean
    fun resetPasswordByIdUserUseCase(userFindByIdUseCase: UserFindByIdUseCase, gateway: UserGateway) =
        UserResetPasswordByIdUseCaseImpl(userFindByIdUseCase, gateway)

}