package com.dnlab.dway.extension

import com.dnlab.dway.support.DataCleaner
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class DataCleanerExtension : AfterEachCallback {
    override fun afterEach(context: ExtensionContext) =
        with(SpringExtension.getApplicationContext(context).getBean("dataCleaner") as DataCleaner) {
            execute()
        }
}