package com.dnlab.dway.region.domain

import jakarta.persistence.*

@Entity
class Airport(
    @Id
    @Column(columnDefinition = "CHAR(3)")
    val id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val country: Country,

    @Enumerated(EnumType.STRING)
    val regionCategory: RegionCategory,
    val regionName: String,
    val korName: String,
    val engName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Airport

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}