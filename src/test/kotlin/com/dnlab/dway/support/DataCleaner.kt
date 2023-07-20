package com.dnlab.dway.support

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Profile("test")
class DataCleaner {

    private val foreignKeyCheckFormat = "SET FOREIGN_KEY_CHECKS = 0"
    private val truncateFormat = "TRUNCATE TABLE %s"

    private lateinit var tableNames: List<String>

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @PostConstruct
    fun findDatabaseTableNames() {
        val tableInfos = entityManager.createNativeQuery("SHOW TABLES").resultList
        tableNames = tableInfos.map { it as String }
    }

    @Transactional
    fun execute() {
        entityManager.clear()
        executeTruncate()
    }

    private fun executeTruncate() {
        entityManager.createNativeQuery(String.format(foreignKeyCheckFormat, 0)).executeUpdate()
        tableNames.forEach { entityManager.createNativeQuery(String.format(truncateFormat, it)).executeUpdate() }
        entityManager.createNativeQuery(String.format(foreignKeyCheckFormat, 1)).executeUpdate()
    }
}