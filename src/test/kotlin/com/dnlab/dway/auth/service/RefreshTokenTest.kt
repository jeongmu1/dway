package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.JwtProperties
import com.dnlab.dway.auth.domain.Token
import com.dnlab.dway.auth.dto.RefreshTokenRequestDto
import com.dnlab.dway.auth.exception.InvalidTokenException
import com.dnlab.dway.auth.repository.AuthorityRepository
import com.dnlab.dway.auth.repository.MemberRepository
import com.dnlab.dway.auth.repository.TokenRepository
import com.dnlab.dway.region.repository.CountryRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder

class RefreshTokenTest {
    private lateinit var authService: AuthService

    private val tokenService: TokenService =
        TokenService(
            JwtProperties(
                secret = "asdfhakjlsfhdjklfhadfkjadlsfjkdasfhajklsadhfjkasdlkjhasdjlfhaslds",
                header = "header",
                accessTokenExpiration = 1000L,
                refreshTokenExpiration = 1000L
            )
        )

    @Mock
    private lateinit var tokenRepository: TokenRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        authService = AuthServiceImpl(
            tokenService = tokenService,
            memberRepository = mock(MemberRepository::class.java),
            authorityRepository = mock(AuthorityRepository::class.java),
            passwordEncoder = mock(PasswordEncoder::class.java),
            tokenRepository = tokenRepository,
            countryRepository = mock(CountryRepository::class.java),
            authenticationManagerBuilder = mock(AuthenticationManagerBuilder::class.java)
        )
    }

    @Test
    fun refreshToken_success() {
        val refreshToken = "refreshToken"

        `when`(tokenRepository.findTokenByToken(anyString())).thenReturn(
            Token(
                token = refreshToken,
                username = "username"
            )
        )

        assertNotNull(authService.refreshToken(RefreshTokenRequestDto(refreshToken)))
    }

    @Test
    fun refreshToken_invalidToken() {
        val refreshToken = "wrongToken"

        `when`(tokenRepository.findTokenByToken(anyString())).thenReturn(null)

        assertThrows<InvalidTokenException> { authService.refreshToken(RefreshTokenRequestDto(refreshToken)) }
    }

}