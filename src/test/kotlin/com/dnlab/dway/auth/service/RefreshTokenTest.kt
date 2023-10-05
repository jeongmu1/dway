package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.JwtProperties
import com.dnlab.dway.auth.domain.Authority
import com.dnlab.dway.auth.domain.Role
import com.dnlab.dway.auth.domain.Token
import com.dnlab.dway.auth.dto.request.RefreshTokenRequestDto
import com.dnlab.dway.auth.exception.InvalidTokenException
import com.dnlab.dway.auth.repository.AuthorityRepository
import com.dnlab.dway.auth.repository.TokenRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.mockito.kotlin.any

internal class RefreshTokenTest : FunSpec({
    val tokenRepository = mockk<TokenRepository>()
    val authorityRepository = mockk<AuthorityRepository>()

    val tokenService = TokenService(
        JwtProperties(
            secret = "asdfhakjlsfhdjklfhadfkjadlsfjkdasfhajklsadhfjkasdlkjhasdjlfhaslds",
            header = "header",
            accessTokenExpiration = 1000L,
            refreshTokenExpiration = 1000L
        )
    )

    val authService = AuthServiceImpl(
        tokenService = tokenService,
        memberRepository = mockk(),
        authorityRepository = authorityRepository,
        passwordEncoder = mockk(),
        tokenRepository = tokenRepository,
        countryRepository = mockk(),
        authenticationManagerBuilder = mockk()
    )

    context("토큰 재발급") {
        test("올바른 토큰이 입력되었을 경우 널이 아닌 값을 반환해야 한다") {
            val refreshToken = "refreshToken"

            every { tokenRepository.findTokenByToken(any()) } returns Token(
                token = refreshToken,
                username = "username"
            )

            every { tokenRepository.delete(any()) } returns any()
            every { tokenRepository.save(any()) } returns any()
            every { authorityRepository.findAuthoritiesByMemberUsername(any()) } returns setOf(
                Authority(
                    id = 0L,
                    member = mockk(),
                    role = Role.ROLE_MEMBER
                )
            )

            authService.refreshToken(RefreshTokenRequestDto(refreshToken)) shouldNotBe null
        }

        test("올바르지 않은 토큰이 입력되었을 경우 예외를 던져야 한다") {
            val refreshToken = "wrongToken"

            every { tokenRepository.findTokenByToken(any()) } returns null
            every { tokenRepository.delete(any()) } returns any()

            shouldThrow<InvalidTokenException> {
                authService.refreshToken(RefreshTokenRequestDto(refreshToken))
            }
        }
    }
})