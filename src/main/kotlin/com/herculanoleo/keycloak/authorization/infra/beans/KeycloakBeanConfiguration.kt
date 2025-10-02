package com.herculanoleo.keycloak.authorization.infra.beans

import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean

@ConfigurationProperties(prefix = "api.keycloak")
class KeycloakBeanConfiguration(
    val url: String = "",
    val realm: String = "",
    val clientId: String = "",
    val clientSecret: String = "",
    val grantType: String = org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS,
    val roleMapKeys: Set<String> = emptySet(),
) {

    @Bean
    fun keycloakAdminClient(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl(url)
            .realm(realm)
            .grantType(grantType)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scope("email profile openid")
            .build()
    }

    @Bean("keycloakAdminRealmResource")
    fun keycloakAdminRealmResource(
        @Qualifier("keycloakAdminClient") keycloakAdminClient: Keycloak,
    ): RealmResource = keycloakAdminClient.realm(realm)

    @Bean("keycloakAdminUsersResource")
    fun keycloakAdminUsersResource(
        @Qualifier("keycloakAdminRealmResource") realmResource: RealmResource
    ): UsersResource = realmResource.users()

    @Bean("keycloakAdminGroupsResource")
    fun keycloakAdminGroupResource(
        @Qualifier("keycloakAdminRealmResource") realmResource: RealmResource
    ): GroupsResource = realmResource.groups()

    @Bean("keycloakAdminRolesResource")
    fun keycloakAdminRolesResource(
        @Qualifier("keycloakAdminRealmResource") realmResource: RealmResource
    ): RolesResource = realmResource.roles()

    @Bean("keycloakAdminClientsResource")
    fun keycloakAdminClientsResource(
        @Qualifier("keycloakAdminRealmResource") realmResource: RealmResource
    ): ClientsResource = realmResource.clients()

}