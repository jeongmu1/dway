package com.dnlab.dway.auth.exception

import org.springframework.security.core.AuthenticationException

class InvalidTokenException(msg: String?) : AuthenticationException(msg)
