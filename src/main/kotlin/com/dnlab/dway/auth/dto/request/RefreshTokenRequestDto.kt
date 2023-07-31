package com.dnlab.dway.auth.dto.request

import jakarta.validation.constraints.NotNull

data class RefreshTokenRequestDto(
    @NotNull(message = "RefreshToken 이 포함되어야 합니다")
    val refreshToken: String
)