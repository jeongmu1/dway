package com.dnlab.dway

import com.dnlab.dway.auth.config.JwtProperties
import com.dnlab.dway.common.service.BatchService
import com.dnlab.dway.region.domain.Airport
import com.dnlab.dway.region.domain.Country
import com.dnlab.dway.region.domain.RegionCategory
import com.dnlab.dway.region.repository.CountryRepository
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
    fun dataLoader(batchService: BatchService, countryRepository: CountryRepository) = CommandLineRunner {
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

        val korea = countryRepository.findCountryById("KR")!!
        val japan = countryRepository.findCountryById("JP")!!
        batchService.airportBatchInsert(
            listOf(
                Airport(
                    id = "ICN",
                    korName = "인천국제공항",
                    engName = "Incheon International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "서울/인천",
                    country = korea
                ),
                Airport(
                    id = "GMP",
                    korName = "김포국제공항",
                    engName = "Gimpo International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "서울/김포",
                    country = korea
                ),
                Airport(
                    id = "TAE",
                    korName = "대구국제공항",
                    engName = "Daegu International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "대구",
                    country = korea
                ),
                Airport(
                    id = "CJU",
                    korName = "제주국제공항",
                    engName = "Jeju International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "제주",
                    country = korea
                ),
                Airport(
                    id = "KWJ",
                    korName = "광주국제공항",
                    engName = "Gwangju International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "광주",
                    country = korea
                ),
                Airport(
                    id = "PUS",
                    korName = "김해국제공항",
                    engName = "Gimhae International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "부산/김해",
                    country = korea
                ),
                Airport(
                    id = "CJJ",
                    korName = "청주국제공항",
                    engName = "Cheongju International Airport",
                    regionCategory = RegionCategory.KOREA,
                    regionName = "청주",
                    country = korea
                ),
                Airport(
                    id = "KOJ",
                    korName = "가고시마공항",
                    engName = "Kagoshima Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "가고시마",
                    country = japan
                ),
                Airport(
                    id = "KMJ",
                    korName = "구마모토공항",
                    engName = "Kumamoto Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "구마모토",
                    country = japan
                ),
                Airport(
                    id = "NRT",
                    korName = "나리타국제공항",
                    engName = "Narita International Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "도쿄/나리타",
                    country = japan
                ),
                Airport(
                    id = "HSG",
                    korName = "사가공항",
                    engName = "Saga Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "사가",
                    country = japan
                ),
                Airport(
                    id = "CTS",
                    korName = "신치토세공항",
                    engName = "New Chitose Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "삿포로",
                    country = japan
                ),
                Airport(
                    id = "KIX",
                    korName = "간사이국제공항",
                    engName = "Kansai International Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "오사카/간사이",
                    country = japan
                ),
                Airport(
                    id = "OKA",
                    korName = "나하공항",
                    engName = "Naha Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "오키나와",
                    country = japan
                ),
                Airport(
                    id = "FUK",
                    korName = "후쿠오카공항",
                    engName = "Fukuoka Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "후쿠오카",
                    country = japan
                ),
                Airport(
                    id = "OIT",
                    korName = "오이타공항",
                    engName = "Oita Airport",
                    regionCategory = RegionCategory.JAPAN,
                    regionName = "오이타",
                    country = japan
                )
            )
        )
    }
}

fun main(args: Array<String>) {
    runApplication<DwayApplication>(*args)
}
