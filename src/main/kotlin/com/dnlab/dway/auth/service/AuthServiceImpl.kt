package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.exception.DuplicatedUsernameException
import com.dnlab.dway.auth.domain.Authority
import com.dnlab.dway.auth.domain.Member
import com.dnlab.dway.auth.domain.Role
import com.dnlab.dway.auth.domain.Token
import com.dnlab.dway.auth.dto.request.LoginRequestDto
import com.dnlab.dway.auth.dto.request.RefreshTokenRequestDto
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
import com.dnlab.dway.auth.dto.request.TokenValidationRequestDto
import com.dnlab.dway.auth.dto.response.LoginResponseDto
import com.dnlab.dway.auth.dto.response.RegistrationResponseDto
import com.dnlab.dway.auth.dto.response.TokenValidationResponseDto
import com.dnlab.dway.auth.exception.InvalidTokenException
import com.dnlab.dway.auth.repository.AuthorityRepository
import com.dnlab.dway.auth.repository.MemberRepository
import com.dnlab.dway.auth.repository.TokenRepository
import com.dnlab.dway.region.domain.Country
import com.dnlab.dway.region.repository.CountryRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val tokenService: TokenService,
    private val memberRepository: MemberRepository,
    private val authorityRepository: AuthorityRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenRepository: TokenRepository,
    private val countryRepository: CountryRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) : AuthService {

    @Transactional
    override fun processRegistration(requestDto: RegistrationRequestDto): RegistrationResponseDto {
        require(!memberRepository.existsMemberByUsername(requestDto.username)) {
            throw DuplicatedUsernameException("아이디가 이미 존재합니다.")
        }
        require(requestDto.password == requestDto.passwordConfirm) {
            throw IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
        }

        val phoneCountry = findCountryById(requestDto.phoneCountry, CountryOf.PHONE)
        val country = findCountryById(requestDto.country, CountryOf.RESIDENCE)

        val member = memberRepository.save(
            Member(
                username = requestDto.username,
                password = passwordEncoder.encode(requestDto.password),
                korFirstName = requestDto.korFirstName,
                korLastName = requestDto.korLastName,
                engFirstName = requestDto.engFirstName,
                engLastName = requestDto.engLastName,
                gender = requestDto.gender,
                birthDay = requestDto.birthDay,
                email = requestDto.email,
                phoneCountry = phoneCountry,
                phoneNumber = requestDto.phone,
                country = country
            )
        )

        authorityRepository.save(
            Authority(
                member = member,
                role = Role.ROLE_MEMBER
            )
        )

        return RegistrationResponseDto(
            username = member.username,
            name = "${member.korFirstName}${member.korLastName}"
        )
    }

    @Transactional
    override fun processLogin(requestDto: LoginRequestDto): LoginResponseDto {
        val authentication = createAuthentication(requestDto)

        val accessToken = tokenService.createAccessToken(authentication)
        val refreshToken = tokenService.createRefreshToken()

        val tokens =
            tokenRepository.findTokensByUsername(requestDto.username) // 해당 사용자의 모든 RefreshToken 제거 후 새 RefreshToken 저장
        tokenRepository.deleteAll(tokens)
        val newToken = Token(refreshToken, requestDto.username)
        tokenRepository.save(newToken)

        return LoginResponseDto(accessToken = accessToken, refreshToken = refreshToken)
    }

    override fun refreshToken(requestDto: RefreshTokenRequestDto): LoginResponseDto {
        val refreshToken = requestDto.refreshToken
        val prevTokenInfo =
            tokenRepository.findTokenByToken(refreshToken)
                ?: throw InvalidTokenException("잘못되거나 만료된 토큰입니다.")
        tokenRepository.delete(prevTokenInfo)

        val newRefreshToken = tokenService.createRefreshToken()
        tokenRepository.save(Token(token = newRefreshToken, username = prevTokenInfo.username))

        val grantedAuthorities =
            authorityRepository.findAuthoritiesByMemberUsername(prevTokenInfo.username)
                .map { GrantedAuthority { it.role.toString() } }

        return LoginResponseDto(
            accessToken = tokenService.createAccessToken(
                UsernamePasswordAuthenticationToken(
                    prevTokenInfo.username,
                    null,
                    grantedAuthorities
                )
            ),
            refreshToken
        )
    }

    override fun checkTokenValidation(requestDto: TokenValidationRequestDto): TokenValidationResponseDto {
        return TokenValidationResponseDto(tokenService.validateToken(requestDto.accessToken))
    }

    private fun createAuthentication(requestDto: LoginRequestDto): Authentication {
        try {
            val authenticationToken =
                UsernamePasswordAuthenticationToken(requestDto.username, requestDto.password)
            val authentication =
                authenticationManagerBuilder.`object`.authenticate(authenticationToken)
            SecurityContextHolder.getContext().authentication = authentication
            return authentication
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("아이디 또는 비밀번호가 잘못되었습니다.")
        }
    }

    private fun findCountryById(id: String, countryOf: CountryOf): Country {
        val country = countryRepository.findCountryById(id)
        checkNotNull(country) {
            val message: String = when (countryOf) {
                CountryOf.RESIDENCE -> "거주"
                CountryOf.PHONE -> "휴대전화"
            }

            throw NoSuchElementException("해당 $message 국가를 찾을 수 없습니다: $id")
        }
        return country
    }

    private enum class CountryOf {
        RESIDENCE,
        PHONE
    }
}
