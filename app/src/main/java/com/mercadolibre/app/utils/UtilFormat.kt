package com.mercadolibre.app.utils

import android.util.Base64
import java.text.NumberFormat
import java.util.*

object UtilFormat {
    fun moneyFormat(number: Int): String {
        return NumberFormat.getCurrencyInstance(Locale("en", "US")).format(number)
    }

    fun String.decode(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
    }

    fun String.encode(): String {
        return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    }
}

