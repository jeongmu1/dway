package com.dnlab.dway.auth.config

import com.dnlab.dway.auth.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val tokenService: TokenService,
    private val corsFilter: CorsFilter,
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val accessDeniedHandler: JwtAccessDeniedHandler
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain = with(httpSecurity) {
        apply(JwtSecurityConfig(tokenService))
        addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
        csrf(AbstractHttpConfigurer<*, *>::disable)
        exceptionHandling {
            it.authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        }
        sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        authorizeHttpRequests { it.requestMatchers("/api/auth/**").permitAll() }
    }.orBuild

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}