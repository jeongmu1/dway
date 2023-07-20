package com.dnlab.dway.common.service

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
                    ps.setString(1, countries[i].id)
                    ps.setString(2, countries[i].code3)
                    ps.setShort(3, countries[i].numberCode)
                    ps.setString(4, countries[i].korName)
                    ps.setString(5, countries[i].engName)
                }

                override fun getBatchSize() = min(hibernateBatchSize, countries.size)

            })
    }
}