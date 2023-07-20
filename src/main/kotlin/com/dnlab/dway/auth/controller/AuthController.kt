package com.dnlab.dway.auth.controller

import com.dnlab.dway.auth.dto.*
import com.dnlab.dway.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/registration")
    fun processRegistration(@RequestBody requestDto: RegistrationRequestDto): ResponseEntity<RegistrationResponseDto> {
        return ResponseEntity.ok(authService.processRegistration(requestDto))
    }

    @PostMapping("/login")
    fun processLogin(@RequestBody requestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(authService.processLogin(requestDto))
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody requestDto: RefreshTokenRequestDto): ResponseEntity<LoginResponseDto> {
        TODO("Not Yet Implemented")
    }
}