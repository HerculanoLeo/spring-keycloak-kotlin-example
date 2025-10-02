package com.herculanoleo.keycloak.common.infra.beans

import com.herculanoleo.keycloak.authorization.infra.beans.KeycloakBeanConfiguration
import com.herculanoleo.keycloak.authorization.infra.models.decorators.BearerTokenAuthenticationTokenDecorator
import com.herculanoleo.keycloak.authorization.infra.models.decorators.OAuth2AuthenticationTokenDecorator
import com.herculanoleo.keycloak.authorization.infra.models.dtos.KeycloakUser
import com.herculanoleo.keycloak.common.core.extensions.lastPath
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component

@Component
class DecoratorAuthenticationManager(
    private val jwtDecoder: JwtDecoder,
    private val keycloakConfig: KeycloakBeanConfiguration,
) : AuthenticationManager {

    private val logger = org.slf4j.LoggerFactory.getLogger(DecoratorAuthenticationManager::class.java)

    override fun authenticate(authentication: Authentication): Authentication {
        if (authentication is BearerTokenAuthenticationToken) {
            try {
                return authenticate(authentication, authentication.token)
            } catch (ex: JwtException) {
                logger.debug("Failed to decode JWT token", ex)
            }
        }

        if (authentication is OAuth2AuthenticationToken && authentication.principal is OidcUser) {
            try {
                val principal = authentication.principal as OidcUser
                return authenticate(authentication, principal.idToken.tokenValue)
            } catch (ex: JwtException) {
                logger.debug("Failed to decode JWT token", ex)
            }
        }

        return authentication
    }

    fun authenticate(authentication: BearerTokenAuthenticationToken, token: String): Authentication {
        val user = keycloakUser(token)
        return BearerTokenAuthenticationTokenDecorator(
            authentication,
            user,
            user.roles.map { SimpleGrantedAuthority(it) }
        )
    }

    fun authenticate(authentication: OAuth2AuthenticationToken, token: String): Authentication {
        val user = keycloakUser(token)
        return OAuth2AuthenticationTokenDecorator(
            authentication,
            user,
            user.roles.map { SimpleGrantedAuthority(it) })
    }

    fun keycloakUser(token: String): KeycloakUser {
        val jwt = jwtDecoder.decode(token)

        val userInfo = this.getUserInfo(jwt)

        val roles = roles(userInfo, keycloakConfig.roleMapKeys.toList())

        return KeycloakUser(
            userInfo,
            tokenType(jwt),
            token,
            realm(jwt),
            clientId(jwt),
            roles
        )
    }

    fun getUserInfo(jwt: Jwt): OidcUserInfo {
        return OidcUserInfo(jwt.claims)
    }

    fun tokenType(jwt: Jwt): String {
        val typ = jwt.claims["typ"]
        return typ as? String ?: "Bearer"
    }

    fun clientId(jwt: Jwt): String {
        val azp = jwt.claims["azp"]
        return azp as? String ?: ""
    }

    fun realm(jwt: Jwt): String {
        val issuer = jwt.claims["issuer"]
        if (issuer is String) {
            return issuer.lastPath()
        }
        return ""
    }

    fun roles(obj: Any?, keys: List<String>): Collection<String> {
        return when (obj) {
            is OidcUserInfo -> roles(obj, keys)
            is Map<*, *> -> roles(obj, keys)
            is List<*> -> roles(obj)
            else -> emptyList()
        }
    }

    fun roles(userInfo: OidcUserInfo, keys: List<String>): Collection<String> {
        val roles: MutableSet<String> = HashSet()
        for (key in keys) {
            roles.addAll(roles(userInfo.claims[key], keys))
        }
        return roles
    }

    fun roles(map: Map<*, *>, keys: List<String>): Collection<String> {
        val roles: MutableSet<String> = HashSet()
        for ((_, value) in map) {
            roles.addAll(roles(value, keys))
        }
        return roles
    }

    fun roles(list: List<*>): Collection<String> {
        return list.map { it.toString() }.toSet()
    }

}