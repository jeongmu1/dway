package com.dnlab.dway.auth.controller

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class TokenExceptionAdvice {

    @ExceptionHandler(SignatureException::class)
    fun handleSecurityException() = ResponseEntity("서명 검증에 실패하였습니다.", HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException() = ResponseEntity("토큰 형식이 올바르지 않습니다", HttpStatus.BAD_REQUEST)

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException() = ResponseEntity("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(UnsupportedJwtException::class)
    fun handleUnsupportedJwtException() = ResponseEntity("지원하지 않는 JWT 형식입니다.", HttpStatus.BAD_REQUEST)

    @ExceptionHandler(JwtException::class)
    fun handleJwtException() = ResponseEntity("JWT 처리 중에 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
}