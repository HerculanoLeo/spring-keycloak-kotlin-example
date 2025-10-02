package com.herculanoleo.keycloak.authorization.infra.presentation

import com.herculanoleo.keycloak.authorization.core.domain.user.UserCreate
import com.herculanoleo.keycloak.authorization.core.domain.user.UserSearch
import com.herculanoleo.keycloak.authorization.core.domain.user.UserUpdate
import com.herculanoleo.keycloak.authorization.core.usecases.user.*
import com.herculanoleo.keycloak.authorization.infra.mapper.user.UserMapper
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(
    private val searchUseCase: UserSearchUseCase,
    private val findByIdUseCase: UserFindByIdUseCase,
    private val createUseCase: UserCreateUseCase,
    private val updateUseCase: UserUpdateUseCase,
    private val activeUseCase: UserActiveByIdUseCase,
    private val inactiveUseCase: UserInactiveByIdUseCase,
    private val resetPasswordUseCase: UserResetPasswordByIdUseCase,
    private val userMapper: UserMapper
) {

    @GetMapping
    fun search(requestEntity: UserSearch) = searchUseCase.execute(requestEntity).let {
        ResponseEntity.ok(it.map { user -> userMapper.dto(user) })
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: String) = findByIdUseCase.execute(id)?.let {
        ResponseEntity.ok(userMapper.dto(it))
    } ?: throw NotFoundException("User not found")

    @PostMapping
    fun create(@RequestBody requestEntity: UserCreate) = createUseCase.execute(requestEntity).let {
        ResponseEntity.status(HttpStatus.CREATED).body(userMapper.dto(it))
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: String, @RequestBody requestEntity: UserUpdate): ResponseEntity<Unit> =
        updateUseCase.execute(id, requestEntity).let { ResponseEntity.noContent().build() }

    @PutMapping("{id}/active")
    fun active(@PathVariable id: String): ResponseEntity<Unit> = activeUseCase.execute(id).let {
        ResponseEntity.accepted().build()
    }

    @DeleteMapping("{id}/inactive")
    fun inactive(@PathVariable id: String): ResponseEntity<Unit> = inactiveUseCase.execute(id).let {
        ResponseEntity.accepted().build()
    }

    @PutMapping("{id}/reset-password")
    fun resetPassword(@PathVariable id: String): ResponseEntity<Unit> = resetPasswordUseCase.execute(id).let {
        ResponseEntity.accepted().build()
    }

}