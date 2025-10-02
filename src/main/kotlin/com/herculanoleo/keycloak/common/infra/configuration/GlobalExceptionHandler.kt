package com.herculanoleo.keycloak.common.infra.configuration

import com.herculanoleo.keycloak.common.core.domain.exceptions.BadRequestException
import com.herculanoleo.keycloak.common.core.domain.exceptions.ConflictException
import com.herculanoleo.keycloak.common.core.domain.exceptions.NotFoundException
import com.herculanoleo.keycloak.common.core.domain.exceptions.ServerExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime


@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDenied(ex: Exception): ResponseEntity<ServerExceptionResponse> {
        return this.buildResponseEntity(HttpStatus.FORBIDDEN, ex.localizedMessage)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFound(ex: Exception): ResponseEntity<ServerExceptionResponse> {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.message)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequest(ex: Exception): ResponseEntity<ServerExceptionResponse> {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.message)
    }

    @ExceptionHandler(ConflictException::class)
    fun conflict(ex: Exception): ResponseEntity<ServerExceptionResponse> {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ServerExceptionResponse> {
        logger.error("An unexpected error has occurred, contact the Administrator.", ex)
        return buildResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error has occurred, contact the Administrator."
        )
    }

    private fun buildResponseEntity(
        httpStatus: HttpStatus,
        message: String?
    ): ResponseEntity<ServerExceptionResponse> {
        return ResponseEntity
            .status(httpStatus)
            .body(ServerExceptionResponse(message, OffsetDateTime.now()))
    }

}