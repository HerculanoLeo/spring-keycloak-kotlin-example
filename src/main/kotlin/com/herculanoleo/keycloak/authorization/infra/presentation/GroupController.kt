package com.herculanoleo.keycloak.authorization.infra.presentation

import com.herculanoleo.keycloak.authorization.core.domain.group.GroupRegister
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupSearch
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupUpdate
import com.herculanoleo.keycloak.authorization.core.usecases.group.*
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("groups")
class GroupController(
    private val searchUseCase: GroupSearchUseCase,
    private val findByIdUseCase: GroupFindByIdUseCase,
    private val createUseCase: GroupCreateUseCase,
    private val updateUseCase: GroupUpdateUseCase,
    private val deleteUseCase: GroupDeleteUseCase,
) {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('groups-read')")
    fun search(requestEntity: GroupSearch) = searchUseCase.execute(requestEntity).let {
        ResponseEntity.ok(it)
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('groups-read')")
    fun findById(@PathVariable id: String) = findByIdUseCase.execute(id)?.let {
        ResponseEntity.ok(it)
    } ?: throw NotFoundException("Group not found")

    @PostMapping
    @PreAuthorize("hasAnyAuthority('groups-write')")
    fun create(@RequestBody requestEntity: GroupRegister) = createUseCase.execute(requestEntity).let {
        ResponseEntity.status(201).body(it)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('groups-write')")
    fun update(@PathVariable id: String, @RequestBody requestEntity: GroupUpdate): ResponseEntity<Unit> =
        updateUseCase.execute(id, requestEntity).let { ResponseEntity.noContent().build() }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('groups-write')")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> = deleteUseCase.execute(id).let {
        ResponseEntity.accepted().build()
    }

}