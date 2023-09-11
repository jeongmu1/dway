package com.dnlab.dway.auth.dto.response

data class UsernameDuplicationResponseDto(
    val username: String,
    val duplicated: Boolean
)
