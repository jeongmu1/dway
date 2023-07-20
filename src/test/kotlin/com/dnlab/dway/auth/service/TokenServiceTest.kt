package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.JwtProperties
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
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
    @DisplayName("Authentication으로 accessToken을 생성할 수 있어야 한다")
    fun createAccessToken() {
        val accessToken = tokenService.createAccessToken(authentication)

        assertTrue(accessToken.isNotBlank())
    }

    @Test
    @DisplayName("RefreshToken을 생성할 수 있어야 한다")
    fun createRefreshToken() {
        assertTrue(tokenService.createRefreshToken().isNotBlank())
    }

    @Test
    @DisplayName("AccessToken으로 부터 Authentication객체를 추출할 수 있어야 한다")
    fun getAuthentication() {
        val authentication = UsernamePasswordAuthenticationToken(
            User("test", "", setOf(SimpleGrantedAuthority("ROLE_MEMBER"))),
            null, setOf(SimpleGrantedAuthority("ROLE_MEMBER"))
        )
        val accessToken = tokenService.createAccessToken(authentication)

        assertTrue(tokenService.getAuthentication(accessToken).isAuthenticated)
    }

    @Test
    @DisplayName("AccessToken의 유효성 검증을 수행할 수 있어야 한다")
    fun validateToken() {
        val token = tokenService.createAccessToken(authentication)
        assertTrue(tokenService.validateToken(token))
    }

    @Test
    @DisplayName("HttpHeader로부터 토큰을 추출할 수 있어야 한다")
    fun resolveToken() {
        val token = tokenService.createAccessToken(authentication)
        val request = mock(HttpServletRequest::class.java)
        `when`(request.getHeader("Authorization")).thenReturn("Bearer $token")
    }
}