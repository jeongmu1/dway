package com.dnlab.dway.common.util

import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.LocalDate

fun DayOfWeek.toKoreanChar(): Char {
    return when(this.value) {
        1 -> '월'
        2 -> '화'
        3 -> '수'
        4 -> '목'
        5 -> '금'
        6 -> '토'
        7 -> '일'
        else -> throw IllegalArgumentException("1~7 범위 내의 정수형이여야 합니다.")
    }
}

fun LocalDate.getStartTimestamp(): Timestamp {
    return Timestamp.valueOf(this.atStartOfDay())
}

fun LocalDate.getEndTimestamp(): Timestamp {
    return Timestamp.valueOf(this.plusDays(1).atStartOfDay())
}
