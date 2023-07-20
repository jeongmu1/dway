package com.dnlab.dway.common.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig(private val redisProperties: RedisProperties) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(
        RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
    )

    @Bean
    fun redisTemplate(): RedisTemplate<Array<Byte>, Array<Byte>> = RedisTemplate<Array<Byte>, Array<Byte>>().apply {
        connectionFactory = redisConnectionFactory()
        keySerializer = StringRedisSerializer()
    }
}