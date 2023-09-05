package com.dnlab.dway.auth.domain

import com.dnlab.dway.common.domain.BaseTimeEntity
import com.dnlab.dway.region.domain.Country
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Date

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 20, unique = true)
    val username: String,

    @Column(length = 60)
    var password: String,

    @Column(length = 2)
    var korFirstName: String,

    @Column(length = 10)
    var korLastName: String,

    @Column(length = 20)
    var engFirstName: String,

    @Column(length = 20)
    var engLastName: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    val gender: Gender,

    val birthDay: Date,

    @Column(length = 60)
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var phoneCountry: Country,

    @Column(length = 13)
    val phoneNumber: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var country: Country,

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