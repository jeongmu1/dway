package com.dnlab.dway.auth.config

import com.dnlab.dway.auth.domain.Authority
import com.dnlab.dway.auth.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
        private val member: Member,
        private val authorities: Set<Authority>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    = authorities.map { GrantedAuthority { it.role.toString() } }.toMutableSet()

    override fun getPassword(): String = member.password

    override fun getUsername(): String = member.username

    override fun isAccountNonExpired(): Boolean = member.enabled

    override fun isAccountNonLocked(): Boolean = member.enabled

    override fun isCredentialsNonExpired(): Boolean = member.enabled

    override fun isEnabled(): Boolean = member.enabled
}