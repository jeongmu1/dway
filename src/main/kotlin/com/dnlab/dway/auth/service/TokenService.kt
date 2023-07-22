package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import java.util.UUID

@Service
class TokenService(private val jwtProperties: JwtProperties) {
    private val authoritiesKey = "com/dnlab/dway"
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))

    fun createAccessToken(authentication: Authentication): String {
        val authorities = authentication.authorities.map { it.authority }.toSet()
        val now = Date()
        val expirationDate = Date(now.time + jwtProperties.accessTokenExpiration)

        return Jwts.builder()
            .setSubject(authentication.name)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .claim(authoritiesKey, authorities)
            .signWith(key, SignatureAlgorithm.HS384)
            .compact()
    }

    fun createRefreshToken(): String {
        val token = UUID.randomUUID().toString()
        val now = Date()
        val expirationDate = Date(now.time + jwtProperties.refreshTokenExpiration)

        return Jwts.builder()
            .setSubject(token)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS384)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaimsByToken(token)
        val authorities = claims[authoritiesKey].toString().split(",").map { SimpleGrantedAuthority(it) }.toSet()
        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String): Boolean {
        getClaimsByToken(token)
        return true
    }

    fun resolveToken(request: HttpServletRequest?): String? {
        val bearerToken = request?.getHeader(jwtProperties.header)
        bearerToken?.let {
            with(bearerToken) {
                if (isNotBlank() && startsWith("Bearer")) return drop(7)
            }
        }
        return null
    }

    private fun getClaimsByToken(token: String): Claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .body
}
