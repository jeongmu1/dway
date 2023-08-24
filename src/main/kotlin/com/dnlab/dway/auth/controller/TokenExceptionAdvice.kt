package com.dnlab.dway.auth.controller

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class TokenExceptionAdvice {

    @ExceptionHandler(SignatureException::class)
    fun handleSecurityException() {
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "서명 검증에 실패하였습니다.")
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException() {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰 형식이 올바르지 않습니다")
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException() {
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.")
    }

    @ExceptionHandler(UnsupportedJwtException::class)
    fun handleUnsupportedJwtException() {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 JWT 형식입니다.")
    }

    @ExceptionHandler(JwtException::class)
    fun handleJwtException() {
       throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "JWT 처리 중에 오류가 발생하였습니다.")
    }
}