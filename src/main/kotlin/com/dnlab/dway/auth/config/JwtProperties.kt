package com.dnlab.dway.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
        val secret: String,
        val header: String,
        val accessTokenExpiration: Long,
        val refreshTokenExpiration: Long
)