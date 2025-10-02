package com.herculanoleo.keycloak.common.core.extensions

import java.net.URI

fun URI.lastPath() = this.path.lastPath()

fun String.lastPath() = this.substringAfterLast("/")