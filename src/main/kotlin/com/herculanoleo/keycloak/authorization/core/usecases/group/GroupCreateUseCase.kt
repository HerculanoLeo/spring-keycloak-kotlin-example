package com.herculanoleo.keycloak.authorization.core.usecases.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupRegister

interface GroupCreateUseCase {
    fun execute(requestEntity: GroupRegister): Group
}