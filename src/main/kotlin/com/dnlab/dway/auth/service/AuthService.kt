package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.dto.request.LoginRequestDto
import com.dnlab.dway.auth.dto.request.RefreshTokenRequestDto
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
import com.dnlab.dway.auth.dto.request.TokenValidationRequestDto
import com.dnlab.dway.auth.dto.response.LoginResponseDto
import com.dnlab.dway.auth.dto.response.RegistrationResponseDto
import com.dnlab.dway.auth.dto.response.TokenValidationResponseDto
import com.dnlab.dway.auth.dto.response.UsernameDuplicationResponseDto


interface AuthService {
    fun processRegistration(requestDto: RegistrationRequestDto): RegistrationResponseDto
    fun processLogin(requestDto: LoginRequestDto): LoginResponseDto
    fun refreshToken(requestDto: RefreshTokenRequestDto): LoginResponseDto
    fun checkTokenValidation(requestDto: TokenValidationRequestDto): TokenValidationResponseDto
    fun checkUsernameDuplication(username: String): UsernameDuplicationResponseDto

    // 제거에 성공하였을 경우 true 반환
    fun processLogout(username: String): Boolean
}