package com.dnlab.dway.flight.domain

import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.region.domain.Country
import jakarta.persistence.*
import java.sql.Date

@Entity
class PassengerInfo(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        val ticket: Ticket,

        @ManyToOne(fetch = FetchType.LAZY)
        val country: Country,

        @Column(length = 45)
        val firstName: String,

        @Column(length = 45)
        val lastName: String,

        val gender: Gender,

        val birthDay: Date
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PassengerInfo

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}