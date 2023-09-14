package com.dnlab.dway.region.repository.impl

import com.dnlab.dway.region.domain.QCountry
import com.dnlab.dway.region.dto.response.CountryDto
import com.dnlab.dway.region.repository.CountryCustomRepository
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class CountryCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CountryCustomRepository {
    override fun findAllAsDto(): List<CountryDto> {
        val qCountry = QCountry.country

        return jpaQueryFactory.select(
            Projections.fields(
                CountryDto::class.java,
                qCountry.id.`as`("code2"),
                qCountry.code3.`as`("code3"),
                qCountry.engName.`as`("engName"),
                qCountry.korName.`as`("korName"),
                qCountry.numberCode.`as`("numCode")
            )
        ).from(qCountry).fetch()
    }
}