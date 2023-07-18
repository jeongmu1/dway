package com.dnlab.dway.region.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Country(
        @Id
        val id: String,
        val code3: String,
        val korName: String,
        val engName: String,
        val numberCode: Short,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Country

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}