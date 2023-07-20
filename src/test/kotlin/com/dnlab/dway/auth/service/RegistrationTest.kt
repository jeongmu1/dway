package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.DuplicatedUsernameException
import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.auth.domain.Member
import com.dnlab.dway.auth.dto.RegistrationRequestDto
import com.dnlab.dway.auth.repository.AuthorityRepository
import com.dnlab.dway.auth.repository.MemberRepository
import com.dnlab.dway.auth.repository.TokenRepository
import com.dnlab.dway.region.domain.Country
import com.dnlab.dway.region.repository.CountryRepository
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import java.sql.Date

internal class RegistrationTest {

    private lateinit var authService: AuthService

    @Mock
    private lateinit var memberRepository: MemberRepository

    @Mock
    private lateinit var authorityRepository: AuthorityRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var countryRepository: CountryRepository

    @Captor
    private lateinit var captor: ArgumentCaptor<String>

    @BeforeEach
    fun setup() {
        openMocks(this)
        authService = AuthServiceImpl(
            tokenService = mock(TokenService::class.java),
            memberRepository = memberRepository,
            authorityRepository = authorityRepository,
            passwordEncoder = passwordEncoder,
            tokenRepository = mock(TokenRepository::class.java),
            countryRepository = countryRepository,
            authenticationManagerBuilder = mock(AuthenticationManagerBuilder::class.java)
        )
    }

    @Test
    @DisplayName("중복된 아이디가 있을 경우 예외를 발생시켜야 한다")
    fun processRegistration_DuplicatedUsername() {
        val registrationRequestDto = RegistrationRequestDto(
            username = "testUsername",
            password = "testPassword",
            passwordConfirm = "testPassword",
            korFirstName = "테",
            korLastName = "스트",
            engFirstName = "TE",
            engLastName = "ST",
            gender = Gender.MALE,
            birthDay = Date(Calendar.getInstance().timeInMillis),
            email = "test@test.com",
            phoneCountry = "KR",
            phone = "010-0000-1111",
            country = "KR"
        )

        `when`(memberRepository.existsMemberByUsername(anyString())).thenReturn(true)

        assertThrows(DuplicatedUsernameException::class.java) {
            authService.processRegistration(registrationRequestDto)
        }
    }

    @Test
    @DisplayName("비밀번호와 확인 비밀번호가 일치하지 않을 경우 예외를 발생시켜야 한다")
    fun processRegistration_PasswordMismatch() {
        val registrationRequestDto = RegistrationRequestDto(
            username = "testUsername",
            password = "testPassword123",
            passwordConfirm = "testPassword",
            korFirstName = "테",
            korLastName = "스트",
            engFirstName = "TE",
            engLastName = "ST",
            gender = Gender.MALE,
            birthDay = Date(Calendar.getInstance().timeInMillis),
            email = "test@test.com",
            phoneCountry = "KR",
            phone = "010-0000-1111",
            country = "KR"
        )

        `when`(memberRepository.existsMemberByUsername(anyString())).thenReturn(false)

        assertThrows(IllegalArgumentException::class.java) {
            authService.processRegistration(registrationRequestDto)
        }
    }

    @Test
    fun processRegistration_Success() {
        val password = "password"
        val countryCode = "KR"
        val country = Country(
            id = "KR",
            code3 = "KOR",
            korName = "대한민국",
            engName = "Republic of Korea",
            numberCode = 82
        )
        `when`(memberRepository.existsMemberByUsername(anyString())).thenReturn(false)
        `when`(passwordEncoder.encode(password)).thenReturn("encodedPassword")
        `when`(countryRepository.findCountryById(countryCode)).thenReturn(country)

        val member = Member(
            username = "testUsername",
            password = password,
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
        `when`(memberRepository.save(any())).thenReturn(member)

        val registrationRequestDto = RegistrationRequestDto(
            username = "testUsername",
            password = password,
            passwordConfirm = password,
            korFirstName = "테",
            korLastName = "스트",
            engFirstName = "TE",
            engLastName = "ST",
            gender = Gender.MALE,
            birthDay = Date(Calendar.getInstance().timeInMillis),
            email = "test@test.com",
            phoneCountry = countryCode,
            phone = "010-0000-1111",
            country = countryCode
        )

        authService.processRegistration(registrationRequestDto)

        verify(passwordEncoder).encode(captor.capture())
        assert(captor.value == password)
    }
}


