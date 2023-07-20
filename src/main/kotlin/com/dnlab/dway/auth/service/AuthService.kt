package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.dto.LoginRequestDto
import com.dnlab.dway.auth.dto.LoginResponseDto
import com.dnlab.dway.auth.dto.RegistrationRequestDto
import com.dnlab.dway.auth.dto.RegistrationResponseDto

interface AuthService {
    fun processRegistration(requestDto: RegistrationRequestDto): RegistrationResponseDto
    fun processLogin(requestDto: LoginRequestDto): LoginResponseDto
}