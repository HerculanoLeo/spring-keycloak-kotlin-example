package com.herculanoleo.keycloak.authorization.infra.beans

import org.passay.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean

@ConfigurationProperties(prefix = "api.security.password")
class PasswordConstraintAttributes(
    val upper: Int = 1,
    val digit: Int = 1,
    val lower: Int = 1,
    val special: Int = 1,
) {
    fun generateRules(): List<Rule> {
        return listOf(
            LengthRule(4, 30),
            CharacterRule(EnglishCharacterData.UpperCase, upper),
            CharacterRule(EnglishCharacterData.LowerCase, lower),
            CharacterRule(EnglishCharacterData.Digit, digit),
            CharacterRule(EnglishCharacterData.Special, special),
            WhitespaceRule()
        )
    }

    @Bean
    fun passwordValidator() = PasswordValidator(generateRules())

}