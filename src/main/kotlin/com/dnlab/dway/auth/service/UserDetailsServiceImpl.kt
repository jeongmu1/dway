package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.UserDetailsImpl
import com.dnlab.dway.auth.repository.AuthorityRepository
import com.dnlab.dway.auth.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
        private val memberRepository: MemberRepository,
        private val authorityRepository: AuthorityRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String?): UserDetails {
        val member = username?.let { memberRepository.findMemberByUsername(it) }
                ?: throw UsernameNotFoundException("User not found.")
        return UserDetailsImpl(member = member, authorities = authorityRepository.findAuthoritiesByMember(member))
    }
}