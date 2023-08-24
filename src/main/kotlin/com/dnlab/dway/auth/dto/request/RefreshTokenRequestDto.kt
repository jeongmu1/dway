package com.dnlab.dway.auth.dto.request

import jakarta.validation.constraints.NotNull

data class RefreshTokenRequestDto(
    @NotNull
    val refreshToken: String
)