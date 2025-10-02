package com.herculanoleo.keycloak

import com.herculanoleo.keycloak.authorization.infra.beans.KeycloakBeanConfiguration
import com.herculanoleo.keycloak.authorization.infra.beans.PasswordConstraintAttributes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(KeycloakBeanConfiguration::class, PasswordConstraintAttributes::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
