package com.dnlab.dway.auth.repository

import com.dnlab.dway.auth.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findMemberByUsername(username: String): Member
    fun existsMemberByUsername(username: String): Boolean
}