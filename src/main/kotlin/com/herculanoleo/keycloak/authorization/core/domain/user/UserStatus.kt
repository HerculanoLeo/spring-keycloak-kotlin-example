package com.herculanoleo.keycloak.authorization.core.domain.user

enum class UserStatus {
    ACTIVE("A"),
    INACTIVE("I"),
    ;

    private val value: String

    constructor(value: String) {
        this.value = value
    }

}