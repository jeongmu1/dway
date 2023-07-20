package com.dnlab.dway.auth.dto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)
