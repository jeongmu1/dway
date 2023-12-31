package com.dnlab.dway.common.service

import com.dnlab.dway.flight.domain.Aircraft
import com.dnlab.dway.region.domain.Airport
import com.dnlab.dway.region.domain.Country
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.PreparedStatement
import kotlin.math.min

@Service
class BatchService(
    @Value("\${spring.jpa.properties.hibernate.jdbc.batch_size}") private val hibernateBatchSize: Int,
    private val jdbcTemplate: JdbcTemplate
) {
    @Transactional
    fun countryBatchInsert(countries: List<Country>) {
        jdbcTemplate.batchUpdate("INSERT IGNORE INTO country(id, code3, number_code, kor_name, eng_name) VALUES (?, ?, ?, ?, ?)",
            object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    with(ps) {
                        setString(1, countries[i].id)
                        setString(2, countries[i].code3)
                        setShort(3, countries[i].numberCode)
                        setString(4, countries[i].korName)
                        setString(5, countries[i].engName)
                    }
                }

                override fun getBatchSize(): Int {
                    return min(hibernateBatchSize, countries.size)
                }

            })
    }

    @Transactional
    fun airportBatchInsert(airports: List<Airport>) {
        jdbcTemplate.batchUpdate("INSERT IGNORE INTO airport(id, country_id, eng_name, kor_name, region_category, region_name) VALUES (?, ?, ?, ?, ?, ?)",
            object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    with(ps) {
                        setString(1, airports[i].id)
                        setString(2, airports[i].country.id)
                        setString(3, airports[i].engName)
                        setString(4, airports[i].korName)
                        setString(5, airports[i].regionCategory.toString())
                        setString(6, airports[i].regionName)
                    }
                }

                override fun getBatchSize(): Int {
                    return min(hibernateBatchSize, airports.size)
                }
            })
    }

    @Transactional
    fun aircraftBatchInsert(crafts: List<Aircraft>) {
        jdbcTemplate.batchUpdate("INSERT IGNORE INTO aircraft(manufacture, model) VALUES (?, ?)",
            object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    with(ps) {
                        setString(1, crafts[i].manufacture.toString())
                        setString(2, crafts[i].model)
                    }
                }

                override fun getBatchSize(): Int {
                    return min(hibernateBatchSize, crafts.size)
                }
            })
    }

}