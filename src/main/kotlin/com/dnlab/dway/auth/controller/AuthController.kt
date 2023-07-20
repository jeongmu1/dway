package com.dnlab.dway.auth.controller

import com.dnlab.dway.auth.config.DuplicatedUsernameException
import com.dnlab.dway.auth.dto.*
import com.dnlab.dway.auth.service.AuthService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    @Value("\${jwt.header}") private val authorizationHeader: String
) {
    @PostMapping("/registration")
    fun processRegistration(@RequestBody requestDto: RegistrationRequestDto): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(authService.processRegistration(requestDto))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: DuplicatedUsernameException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        }
    }

    @PostMapping("/login")
    fun processLogin(@RequestBody requestDto: LoginRequestDto): ResponseEntity<*> {
        return try {
            val tokens = authService.processLogin(requestDto)
            val headers = HttpHeaders().apply {
                add(authorizationHeader, "Bearer ${tokens.accessToken}")
            }
            ResponseEntity.ok().headers(headers).body(tokens)
        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
        }
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody requestDto: RefreshTokenRequestDto): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(authService.refreshToken(requestDto))
    }
}