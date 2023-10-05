package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.exception.DuplicatedUsernameException
import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.auth.domain.Member
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
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
import java.time.LocalDate
import kotlin.NoSuchElementException

internal class RegistrationTest {
    private val password = "testPassword"

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
        val registrationRequestDto = createRegistrationRequestDto()
        `when`(memberRepository.existsMemberByUsername(anyString())).thenReturn(true)

        assertThrows(DuplicatedUsernameException::class.java) {
            authService.processRegistration(registrationRequestDto)
        }
    }

    @Test
    @DisplayName("비밀번호와 확인 비밀번호가 일치하지 않을 경우 예외를 발생시켜야 한다")
    fun processRegistration_PasswordMismatch() {
        val registrationRequestDto = createRegistrationRequestDto(withPasswordConfirm = "wrongPassword")
        `when`(memberRepository.existsMemberByUsername(anyString())).thenReturn(false)

        assertThrows(IllegalArgumentException::class.java) {
            authService.processRegistration(registrationRequestDto)
        }
    }

    @Test
    @DisplayName("국가 정보 조회에 실패할 시 예외를 발생시켜야 한다")
    fun processRegistration_NoSuchCountry() {
        val registrationRequestDto = createRegistrationRequestDto()
        `when`(countryRepository.findCountryById(anyString())).thenReturn(null)

        assertThrows(NoSuchElementException::class.java) {
            authService.processRegistration(registrationRequestDto)
        }
    }

    @Test
    fun processRegistration_Success() {
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
            birthDay = LocalDate.now(),
            email = "test@test.com",
            phoneCountry = country,
            phoneNumber = "010-0000-1111",
            country = country
        )
        `when`(memberRepository.save(any())).thenReturn(member)
        val registrationRequestDto = createRegistrationRequestDto()

        authService.processRegistration(registrationRequestDto)

        verify(passwordEncoder).encode(captor.capture())
        assert(captor.value == password)
    }

    private fun createRegistrationRequestDto(withPasswordConfirm: String = password) = RegistrationRequestDto(
        username = "testUsername",
        password = password,
        passwordConfirm = withPasswordConfirm,
        korFirstName = "테",
        korLastName = "스트",
        engFirstName = "TE",
        engLastName = "ST",
        gender = Gender.MALE,
        birthDay = LocalDate.now(),
        email = "test@test.com",
        phoneCountry = "KR",
        phone = "010-0000-1111",
        country = "KR"
    )
}


