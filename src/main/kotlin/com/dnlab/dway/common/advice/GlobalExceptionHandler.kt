package com.dnlab.dway.common.advice

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<*> {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    }
}