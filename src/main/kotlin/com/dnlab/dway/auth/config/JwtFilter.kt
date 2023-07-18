package com.dnlab.dway.auth.config

import com.dnlab.dway.auth.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtFilter(private val tokenService: TokenService) : GenericFilterBean() {
    private val log = LoggerFactory.getLogger(this::class.java)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest

        tokenService.resolveToken(httpServletRequest)?.takeIf { it.isNotBlank() && tokenService.validateToken(it) }?.let { token ->
            val authentication = tokenService.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        } ?: log.debug("유효한 토큰이 발견되지 않음")

        chain.doFilter(request, response)
    }
}