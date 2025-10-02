package com.herculanoleo.keycloak.common.infra.beans

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component

@Component
class DecoratorAuthenticationManager(
    private val jwtDecoder: JwtDecoder,
) : AuthenticationManager {
    override fun authenticate(authentication: Authentication): Authentication {
        //TODO adicionar informações de contexto do usuário aqui com Decorator Pattern
        if (authentication is BearerTokenAuthenticationToken) {
            try {
                jwtDecoder.decode(authentication.token)
                authentication.isAuthenticated = true
            } catch (_: JwtException) {
            }
        }

        if (authentication is OAuth2AuthenticationToken) {
        }

        return authentication
    }
}