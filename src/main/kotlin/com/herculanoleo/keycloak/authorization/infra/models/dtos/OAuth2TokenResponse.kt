package com.herculanoleo.keycloak.authorization.infra.models.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class OAuth2TokenResponse(
    @field:JsonProperty("token_type")
    val tokenType: String,
    @field:JsonProperty("access_token")
    val accessToken: String,
    @field:JsonProperty("expires_in")
    val expiresIn: Long,
    @field:JsonProperty("refresh_token")
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val refreshToken: String? = null,
    @field:JsonProperty("refresh_expires_in")
    val refreshExpiresIn: Long? = null,
    @field:JsonProperty("not-before-policy")
    val notBeforePolicy: Long? = null,
    @field:JsonProperty("session_state")
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val sessionState: String? = null,
    @field:JsonProperty("scope")
    val scope: String? = null,
)
