package com.dnlab.dway.common.util

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.sql.Timestamp
import java.time.LocalDateTime

@Converter
class LocalDateAttributeConverter: AttributeConverter<LocalDateTime, Timestamp> {

    override fun convertToDatabaseColumn(attribute: LocalDateTime?): Timestamp? {
        return Timestamp.valueOf(attribute)
    }

    override fun convertToEntityAttribute(dbData: Timestamp?): LocalDateTime? {
        return dbData?.toLocalDateTime()
    }
}