package com.dnlab.dway.auth.repository

import com.dnlab.dway.auth.domain.Token
import org.springframework.data.repository.CrudRepository

interface TokenRepository: CrudRepository<Token, String>