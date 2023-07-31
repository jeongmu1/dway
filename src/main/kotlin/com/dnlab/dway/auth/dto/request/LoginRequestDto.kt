package com.dnlab.dway.auth.dto.request

import jakarta.validation.constraints.NotNull

data class LoginRequestDto(
    @NotNull(message = "아이디는 반드시 포함되어야 합니다.")
    val username: String,
    @NotNull(message = "비밀번호는 반드시 포함되어야 합니다.")
    val password: String
)
