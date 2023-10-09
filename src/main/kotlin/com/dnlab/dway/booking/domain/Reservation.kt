package com.dnlab.dway.booking.domain

import com.dnlab.dway.common.domain.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Reservation(
    @Id
    val pnr: String
): BaseTimeEntity() {
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    private val _tickets: MutableList<Ticket> = mutableListOf()
    val tickets: List<Ticket>
        get() = _tickets
}