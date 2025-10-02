package com.herculanoleo.keycloak.authorization.infra.presentation

import com.herculanoleo.keycloak.authorization.core.usecases.roles.RolesFindAllUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roles")
class RoleController(
    private val rolesFindAllUseCase: RolesFindAllUseCase
) {
    @GetMapping
    fun findAll() = rolesFindAllUseCase.execute().let { ResponseEntity.ok(it) }
}