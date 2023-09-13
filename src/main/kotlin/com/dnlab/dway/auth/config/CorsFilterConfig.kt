package com.dnlab.dway.auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsFilterConfig(
    @Value("\${client.origin}") private val clientOrigin: String
) {

    @Bean
    fun corsFilter(): CorsFilter {
        return CorsFilter(UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", CorsConfiguration().apply {
                allowCredentials = true
                addAllowedOriginPattern("*")
                addAllowedHeader("*")
                addAllowedMethod("*")
            })
        })
    }
}