package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.dto.*

interface AuthService {
    fun processRegistration(requestDto: RegistrationRequestDto): RegistrationResponseDto
    fun processLogin(requestDto: LoginRequestDto): LoginResponseDto
    fun refreshToken(requestDto: RefreshTokenRequestDto): LoginResponseDto
}