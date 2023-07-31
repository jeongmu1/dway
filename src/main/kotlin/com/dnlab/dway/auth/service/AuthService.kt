package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.dto.request.LoginRequestDto
import com.dnlab.dway.auth.dto.request.RefreshTokenRequestDto
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
import com.dnlab.dway.auth.dto.response.LoginResponseDto
import com.dnlab.dway.auth.dto.response.RegistrationResponseDto

interface AuthService {
    fun processRegistration(requestDto: RegistrationRequestDto): RegistrationResponseDto
    fun processLogin(requestDto: LoginRequestDto): LoginResponseDto
    fun refreshToken(requestDto: RefreshTokenRequestDto): LoginResponseDto
}