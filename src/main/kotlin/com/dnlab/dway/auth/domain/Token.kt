package com.dnlab.dway.auth.domain

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed


@RedisHash(value = "token")
class Token(
    @Id
    @Indexed
    val token: String,
    @Indexed
    val username: String
)