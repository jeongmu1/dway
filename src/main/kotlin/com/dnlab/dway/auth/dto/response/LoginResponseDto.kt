package com.dnlab.dway.auth.dto.response

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)
