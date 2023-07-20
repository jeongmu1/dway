package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.DuplicatedUsernameException
import com.dnlab.dway.auth.domain.Authority
import com.dnlab.dway.auth.domain.Member
import com.dnlab.dway.auth.domain.Role
import com.dnlab.dway.auth.dto.LoginRequestDto
import com.dnlab.dway.auth.dto.LoginResponseDto
import com.dnlab.dway.auth.dto.RegistrationRequestDto
import com.dnlab.dway.auth.dto.RegistrationResponseDto
import com.dnlab.dway.auth.repository.AuthorityRepository
import com.dnlab.dway.auth.repository.MemberRepository
import com.dnlab.dway.auth.repository.TokenRepository
import com.dnlab.dway.region.repository.CountryRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class AuthServiceImpl(
    private val tokenService: TokenService,
    private val memberRepository: MemberRepository,
    private val authorityRepository: AuthorityRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenRepository: TokenRepository,
    private val countryRepository: CountryRepository
) : AuthService {

    @Transactional
    override fun processRegistration(requestDto: RegistrationRequestDto): RegistrationResponseDto {
        require(!memberRepository.existsMemberByUsername(requestDto.username)) {
            throw DuplicatedUsernameException("이미 존재하는 아이디")
        }
        require(requestDto.password == requestDto.passwordConfirm) {
            throw IllegalArgumentException("비밀번호와 확인 불일치")
        }

        val member = memberRepository.save(Member(
            username = requestDto.username,
            password = passwordEncoder.encode(requestDto.password),
            korFirstName = requestDto.korFirstName,
            korLastName = requestDto.korLastName,
            engFirstName = requestDto.engFirstName,
            engLastName = requestDto.engLastName,
            gender = requestDto.gender,
            birthDay = requestDto.birthDay,
            email = requestDto.email,
            phoneCountry = (countryRepository.findCountryById(requestDto.country))!!,
            phoneNumber = requestDto.phone,
            country = (countryRepository.findCountryById(requestDto.country))!!
        ))

        authorityRepository.save(Authority(
            member = member,
            role = Role.ROLE_MEMBER
        ))
        
        return RegistrationResponseDto(username = member.username, name = "${member.korFirstName}${member.korLastName}")
    }

    override fun processLogin(requestDto: LoginRequestDto): LoginResponseDto {
        TODO("Not yet implemented")
    }
}