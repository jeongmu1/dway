package com.dnlab.dway.auth.domain

import com.dnlab.dway.common.domain.BaseTimeEntity
import com.dnlab.dway.region.domain.Country
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 20, unique = true, nullable = false)
    val username: String,

    @Column(length = 60, nullable = false)
    var password: String,

    @Column(length = 2, nullable = false)
    var korFirstName: String,

    @Column(length = 10, nullable = false)
    var korLastName: String,

    @Column(length = 20, nullable = false)
    var engFirstName: String,

    @Column(length = 20, nullable = false)
    var engLastName: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    val gender: Gender,

    @Column(nullable = false)
    val birthDay: LocalDate,

    @Column(length = 60, nullable = false)
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var phoneCountry: Country,

    @Column(length = 13, nullable = false)
    val phoneNumber: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var country: Country,

    @Column(nullable = false)
    var enabled: Boolean = true,
): BaseTimeEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}