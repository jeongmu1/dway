package com.dnlab.dway

import com.dnlab.dway.auth.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
@ConfigurationPropertiesScan
class DwayApplication

fun main(args: Array<String>) {
    runApplication<DwayApplication>(*args)
}
