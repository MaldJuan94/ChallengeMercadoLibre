package com.mercadolibre.app.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mercadolibre.app.BuildConfig
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        runBlocking {

            val packageName = when (BuildConfig.FLAVOR) {
                "develop" -> "com.mercadolibre.dev.${BuildConfig.BUILD_TYPE}"
                "qa" -> "com.mercadolibre.qa.${BuildConfig.BUILD_TYPE}"
                "master" -> "com.mercadolibre.app.${BuildConfig.BUILD_TYPE}"
                else -> "com.mercadolibre.app.${BuildConfig.BUILD_TYPE}"
            }

            print(packageName)
            // Context of the app under test.
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            assertEquals(packageName, appContext.packageName)
        }
    }

    @Test
    fun todoTest() {
        assertNotNull("")
    }
}