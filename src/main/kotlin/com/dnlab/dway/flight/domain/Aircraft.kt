package com.dnlab.dway.flight.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Aircraft(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val model: String,
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