package com.dnlab.dway

import com.dnlab.dway.auth.config.JwtProperties
import com.dnlab.dway.common.service.BatchService
import com.dnlab.dway.region.domain.Country
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
@ConfigurationPropertiesScan
class DwayApplication {

    @Bean
    @Transactional
    fun dataLoader(batchService: BatchService) = CommandLineRunner {
        batchService.countryBatchInsert(
            listOf(
                Country(id = "KR", code3 = "KOR", korName = "대한민국", engName = "Republic of Korea", numberCode = 82),
                Country(id = "JP", code3 = "JPN", korName = "일본", engName = "Japan", numberCode = 81),
                Country(id = "CN", code3 = "CHN", korName = "중화인민공화국", engName = "China", numberCode = 86),
                Country(id = "TH", code3 = "THA", korName = "태국", engName = "Thailand", numberCode = 66),
                Country(id = "TW", code3 = "TWN", korName = "대만", engName = "Taiwan", numberCode = 886),
                Country(id = "VN", code3 = "VNM", korName = "베트남", engName = "Vietnam", numberCode = 84),
                Country(id = "HK", code3 = "HKG", korName = "홍콩", engName = "Hong Kong", numberCode = 852),
                Country(id = "PH", code3 = "PHL", korName = "필리핀", engName = "the Philippines", numberCode = 63),
                Country(id = "MY", code3 = "MYS", korName = "말레이시아", engName = "Malaysia", numberCode = 60),
                Country(id = "RU", code3 = "RUS", korName = "러시아", engName = "Russia", numberCode = 7),
                Country(id = "AU", code3 = "AUS", korName = "오스트레일리아", engName = "Australia", numberCode = 61),
                Country(id = "SG", code3 = "SGP", korName = "싱가포르", engName = "Singapore", numberCode = 65),
            )
        )
    }
}

fun main(args: Array<String>) {
    runApplication<DwayApplication>(*args)
}
