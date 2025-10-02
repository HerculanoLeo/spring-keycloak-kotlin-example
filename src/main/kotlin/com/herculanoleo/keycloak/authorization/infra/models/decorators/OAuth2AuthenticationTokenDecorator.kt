package com.herculanoleo.keycloak.authorization.infra.models.decorators

import com.herculanoleo.keycloak.authorization.infra.models.dtos.KeycloakUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken

class OAuth2AuthenticationTokenDecorator(
    authentication: OAuth2AuthenticationToken,
    val user: KeycloakUser,
    val authorities: Collection<GrantedAuthority>
) :
    OAuth2AuthenticationToken(authentication.principal, authorities, authentication.authorizedClientRegistrationId)