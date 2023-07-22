package com.dnlab.dway.auth.repository

import com.dnlab.dway.auth.domain.Token
import org.springframework.data.repository.CrudRepository

interface TokenRepository : CrudRepository<Token, String> {
    fun findTokensByUsername(username: String): Set<Token>
    fun findTokensByToken(token: String): Set<Token>
    fun findTokenByToken(token: String): Token?
}