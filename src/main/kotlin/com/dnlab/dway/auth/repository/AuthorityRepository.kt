package com.dnlab.dway.auth.repository

import com.dnlab.dway.auth.domain.Authority
import com.dnlab.dway.auth.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository: JpaRepository<Authority, Long> {
    fun findAuthoritiesByMember(member: Member): Set<Authority>
}