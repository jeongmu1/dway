package com.dnlab.dway.auth.domain

import jakarta.persistence.*

@Entity
class Authority(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @ManyToOne(fetch = FetchType.EAGER)
        val member: Member,

        @Enumerated(EnumType.STRING)
        val role: Role
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Authority

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}