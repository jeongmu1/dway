package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.auth.domain.Member
import com.dnlab.dway.auth.dto.request.LoginRequestDto
import com.dnlab.dway.auth.repository.MemberRepository
import com.dnlab.dway.region.domain.Country
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@Transactional
internal class LoginTest {

    @Autowired
    private lateinit var authService: AuthServiceImpl

    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var memberRepository: MemberRepository
    private val username = "test"
    private val password = "test"

    @BeforeEach
    fun setUp() {
        `when`(passwordEncoder.encode(anyString())).thenAnswer { it.arguments.first() }

        val country = Country(
            id = "KR",
            code3 = "KOR",
            korName = "대한민국",
            engName = "Republic of Korea",
            numberCode = 82
        )
        val member = Member(
            username = username,
            password = passwordEncoder.encode(password),
            korFirstName = "테",
            korLastName = "스트",
            engFirstName = "TE",
            engLastName = "ST",
            gender = Gender.MALE,
            birthDay = Date(Calendar.getInstance().timeInMillis),
            email = "test@test.com",
            phoneCountry = country,
            phoneNumber = "010-0000-1111",
            country = country
        )
        memberRepository.save(member)
    }

    @Test
    @DisplayName("로그인 성공 시 토큰을 반환하여야 한다")
    fun processLogin_success() {
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(true)
        val requestDto = LoginRequestDto(username, password)
        val responseDto = authService.processLogin(requestDto)

        assertNotNull(responseDto.accessToken)
        assertNotNull(responseDto.refreshToken)
    }

    @Test
    @DisplayName("잘못된 비밀번호를 입력받을 경우 예외를 반환해야 한다")
    fun processLogin_fail_password() {
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(false)
        val requestDto = LoginRequestDto(username, "wrong")

        assertThrows<BadCredentialsException> { authService.processLogin(requestDto) }
    }

    @Test
    @DisplayName("잘못된 아이디를 입력받을 경우 예외를 반환해야 한다")
    fun processLogin_fail() {
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(false)
        val requestDto = LoginRequestDto("wrong", "wrong")

        assertThrows<BadCredentialsException> { authService.processLogin(requestDto) }
    }
}