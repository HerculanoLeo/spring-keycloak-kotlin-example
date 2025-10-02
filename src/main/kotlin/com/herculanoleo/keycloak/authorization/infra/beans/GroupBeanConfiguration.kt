package com.herculanoleo.keycloak.authorization.infra.beans

import com.herculanoleo.keycloak.authorization.core.gateway.group.GroupGateway
import com.herculanoleo.keycloak.authorization.core.usecases.group.*
import com.herculanoleo.keycloak.authorization.core.usecases.group.impl.*
import com.herculanoleo.keycloak.authorization.core.usecases.roles.RolesFindAllUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupBeanConfiguration {

    @Bean
    fun searchGroupUseCase(gateway: GroupGateway): GroupSearchUseCase = GroupSearchUseCaseImpl(gateway)

    @Bean
    fun findByIdGroupUseCase(gateway: GroupGateway): GroupFindByIdUseCase = GroupFindByIdUseCaseImpl(gateway)

    @Bean
    fun findByUserIdGroupUseCase(gateway: GroupGateway): GroupFindByUserIdUseCase =
        GroupFindByUserIdUseCaseImpl(gateway)

    @Bean
    fun findByByNameGroupUseCase(gateway: GroupGateway): GroupFindByNameUseCase =
        GroupFindByNameUseCaseImpl(gateway)

    @Bean
    fun createGroupUseCase(
        groupFindByNameUseCase: GroupFindByNameUseCase,
        groupUpdateRolesUseCase: GroupUpdateRolesUseCase,
        groupFindByIdUseCase: GroupFindByIdUseCase,
        gateway: GroupGateway
    ): GroupCreateUseCase = GroupCreateUseCaseImpl(
        groupFindByNameUseCase,
        groupUpdateRolesUseCase,
        groupFindByIdUseCase,
        gateway
    )

    @Bean
    fun updateGroupUseCase(
        groupFindByIdUseCase: GroupFindByIdUseCase,
        groupFindByNameUseCase: GroupFindByNameUseCase,
        groupUpdateRolesUseCase: GroupUpdateRolesUseCase,
        gateway: GroupGateway
    ): GroupUpdateUseCase = GroupUpdateUseCaseImpl(
        groupFindByIdUseCase,
        groupFindByNameUseCase,
        groupUpdateRolesUseCase,
        gateway
    )

    @Bean
    fun deleteByIdGroupUseCase(gateway: GroupGateway): GroupDeleteUseCase = GroupDeleteUseCaseImpl(gateway)

    @Bean
    fun updateRolesUseCase(
        gateway: GroupGateway,
        findByIdUseCase: GroupFindByIdUseCase,
        rolesFindAllUseCase: RolesFindAllUseCase
    ): GroupUpdateRolesUseCase = GroupUpdateRolesUseCaseImpl(findByIdUseCase, rolesFindAllUseCase, gateway)

}