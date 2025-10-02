package com.herculanoleo.keycloak.authorization.core.usecases.group

import com.herculanoleo.keycloak.authorization.core.domain.group.Group
import com.herculanoleo.keycloak.authorization.core.domain.group.GroupSearch

interface GroupSearchUseCase {
    fun execute(requestEntity: GroupSearch): List<Group>
}