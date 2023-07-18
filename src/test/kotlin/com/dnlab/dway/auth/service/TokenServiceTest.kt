package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.JwtProperties
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class TokenServiceTest {

    private lateinit var tokenService: TokenService

    private lateinit var jwtProperties: JwtProperties

    private lateinit var authentication: Authentication

    @BeforeEach
    fun setUp() {
        jwtProperties = JwtProperties(
            secret = "asdfhakjlsfhdjklfhadfkjadlsfjkdasfhajklsadhfjkasdlkjhasdjlfhaslds",
            accessTokenExpiration = 10000L,
            refreshTokenExpiration = 20000L,
            header = "Authorization"
        )
        tokenService = TokenService(jwtProperties)
        authentication = UsernamePasswordAuthenticationToken(
            User("test", "", setOf(SimpleGrantedAuthority("ROLE_MEMBER"))),
            null, setOf(SimpleGrantedAuthority("ROLE_MEMBER"))
        )
    }

    @Test
    fun createAccessToken() {
        val accessToken = tokenService.createAccessToken(authentication)

        assertTrue(accessToken.isNotBlank())
    }

    @Test
    fun createRefreshToken() {
        assertTrue(tokenService.createRefreshToken().isNotBlank())
    }

    @Test
    fun getAuthentication() {
        val authentication = UsernamePasswordAuthenticationToken(
            User("test", "", setOf(SimpleGrantedAuthority("ROLE_MEMBER"))),
            null, setOf(SimpleGrantedAuthority("ROLE_MEMBER"))
        )
        val accessToken = tokenService.createAccessToken(authentication)

        assertTrue(tokenService.getAuthentication(accessToken).isAuthenticated)
    }

    @Test
    fun validateToken() {
        val token = tokenService.createAccessToken(authentication)
        assertTrue(tokenService.validateToken(token))
    }

    @Test
    fun resolveToken() {
        val token = tokenService.createAccessToken(authentication)
        val request = mock(HttpServletRequest::class.java)
        `when`(request.getHeader("Authorization")).thenReturn("Bearer $token")
    }
}