package com.mercadolibre.app

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

const val DEFAULT_RETRIES = 5

annotation class RetryOnFailure(val retryCount: Int)

class RetryRule : TestRule {
    override fun apply(statement: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                val retryCount = description
                    .annotations
                    .filterIsInstance<RetryOnFailure>()
                    .firstOrNull()
                    ?.retryCount ?: DEFAULT_RETRIES

                var failureCause: Throwable? = null

                repeat(retryCount + 1) { _ ->
                    runCatching { statement.evaluate() }
                        .onSuccess { return }
                        .onFailure { failureCause = it }
                }

                println("Test ${description.methodName} - Giving up after ${retryCount + 1} attemps")
                failureCause?.printStackTrace()
                assert(false)
            }
        }
    }
}