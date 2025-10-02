package com.herculanoleo.keycloak.common.infra.configuration

import com.herculanoleo.keycloak.common.infra.beans.DecoratorAuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(
        http: HttpSecurity,
        manager: DecoratorAuthenticationManager
    ): SecurityFilterChain = http
        .securityMatcher("/**")
        .authorizeHttpRequests {
            it
                .requestMatchers(
                    "/auth/me",
                    "/openapi/*",
                    "/openapi/*/*",
                    "/*/swagger-ui/*",
                    "/*/swagger-ui.html/*",
                    "/*/swagger-resources/*",
                    "/v3/api-docs",
                    "/v3/api-docs/*",
                    "/actuator/health"
                ).permitAll()
                .requestMatchers("/actuator/**").hasAnyAuthority("observabilidade-sistemas")
                .anyRequest().authenticated()
        }
        .csrf { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .oauth2ResourceServer { it.jwt { jwt -> jwt.authenticationManager(manager) } }
        .build()

}