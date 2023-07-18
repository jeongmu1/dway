package com.dnlab.dway.auth.service

import com.dnlab.dway.auth.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.security.Key
import java.util.Date
import java.util.UUID

@Service
class TokenService(private val jwtProperties: JwtProperties) {
    private val authoritiesKey = "com/dnlab/dway"
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))
    private val log = LoggerFactory.getLogger(this::class.java)

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
        try {
            getClaimsByToken(token)
            return true
        } catch (e: SecurityException) {
            log.info("잘못된 JWT 서명")
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 형식")
        } catch (e: UnsupportedJwtException) {
            log.info("지원하지 않는 JWT")
        } catch (e: IllegalArgumentException) {
            log.info("잘못된 JWT")
        }
        return false
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(jwtProperties.header)
        if (bearerToken.isNotBlank() && bearerToken.startsWith("Bearer ")) return bearerToken.drop(7)
        return null
    }

    private fun getClaimsByToken(token: String): Claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
}
