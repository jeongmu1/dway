package com.dnlab.dway.common.domain

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    @LastModifiedBy
    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set
}