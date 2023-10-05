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

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val ticket: Ticket,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val country: Country,

        @Column(length = 45, nullable = false)
        val firstName: String,

        @Column(length = 45, nullable = false)
        val lastName: String,

        @Column(nullable = false)
        val gender: Gender,

        @Column(length = 9, nullable = false)
        val passportNumber: String,

        @Column(nullable = false)
        val passportExpiration: String,

        @Column(nullable = false)
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