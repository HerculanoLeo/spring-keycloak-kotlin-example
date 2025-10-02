package com.herculanoleo.keycloak.authorization.infra.models.decorators

import com.herculanoleo.keycloak.authorization.infra.models.dtos.KeycloakUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken

class BearerTokenAuthenticationTokenDecorator(
    authentication: BearerTokenAuthenticationToken,
    val user: KeycloakUser,
    val authorities: Collection<GrantedAuthority> = emptyList(),
) :
    BearerTokenAuthenticationToken(authentication.token) {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun isAuthenticated(): Boolean = true

}