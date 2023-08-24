package com.dnlab.dway.auth.controller

import com.dnlab.dway.auth.exception.DuplicatedUsernameException
import com.dnlab.dway.auth.dto.*
import com.dnlab.dway.auth.dto.request.LoginRequestDto
import com.dnlab.dway.auth.dto.request.RefreshTokenRequestDto
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
import com.dnlab.dway.auth.dto.response.LoginResponseDto
import com.dnlab.dway.auth.dto.response.RegistrationResponseDto
import com.dnlab.dway.auth.exception.InvalidTokenException
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
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    @Value("\${jwt.header}") private val authorizationHeader: String
) {
    @PostMapping("/registration")
    fun processRegistration(@RequestBody requestDto: RegistrationRequestDto): ResponseEntity<RegistrationResponseDto> {
        return try {
            ResponseEntity.ok(authService.processRegistration(requestDto))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: DuplicatedUsernameException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, e.message)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PostMapping("/login")
    fun processLogin(@RequestBody requestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        return try {
            val tokens = authService.processLogin(requestDto)
            val headers = HttpHeaders().apply {
                add(authorizationHeader, "Bearer ${tokens.accessToken}")
            }
            ResponseEntity.ok().headers(headers).body(tokens)
        } catch (e: AuthenticationException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        }
    }

    @PostMapping("/reissue")
    fun refreshToken(@RequestBody requestDto: RefreshTokenRequestDto): ResponseEntity<LoginResponseDto> {
        return try {
            ResponseEntity.ok(authService.refreshToken(requestDto))
        } catch (e: InvalidTokenException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        }
    }
}