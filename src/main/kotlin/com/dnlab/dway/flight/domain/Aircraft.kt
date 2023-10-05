package com.dnlab.dway.flight.domain

import jakarta.persistence.*

@Entity
class Aircraft(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
        @Column(unique = true, nullable = false)
        val model: String,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        val manufacture: Manufacture
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Aircraft

                return id == other.id
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }
}