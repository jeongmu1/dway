package com.dnlab.dway.auth.repository

import com.dnlab.dway.auth.domain.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class RedisTest {

    @Autowired
    private lateinit var tokenRepository: TokenRepository

    @Test
    @DisplayName("리프레시 토큰 저장 후 아이디 또는 토큰으로 조회할 수 있어야 한다")
    fun saveRefreshToken() {
        val username = "username"
        val refreshToken = "refreshToken"
        val token = Token(token = refreshToken, username = username)
        tokenRepository.save(token)

        val foundByUsername = tokenRepository.findTokensByUsername(username = "username")
        foundByUsername.forEach {
            assertEquals(username, it.username)
        }

        val foundByToken = tokenRepository.findTokensByToken(refreshToken)
        foundByToken.forEach {
            assertEquals(refreshToken, it.token)
        }

        // tearDown
        tokenRepository.deleteAll(foundByUsername + foundByToken)
    }
}