package com.herculanoleo.keycloak.authorization.infra.models.dtos

import org.springframework.security.oauth2.core.oidc.OidcUserInfo

data class KeycloakUser(
    val info: OidcUserInfo,
    val tokenType: String,
    val accessToken: String,
    val realm: String,
    val clientId: String,
    val roles: Collection<String> = emptyList(),
)
