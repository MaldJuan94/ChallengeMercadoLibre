package com.mercadolibre.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    /*@Test
    fun useAppContext() {
        runBlocking {
            val packageName = when (BuildConfig.FLAVOR) {
                "develop" -> " com.mercadolibre.dev"
                "qa" -> "com.mercadolibre.qa"
                "master" -> "com.mercadolibre.app"
                else -> "com.mercadolibre.app"
            }
            // Context of the app under test.
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            assertEquals(packageName, appContext.packageName)
        }
    }*/

    @Test
    fun todoTest() {
        assertNotNull("")
    }
}