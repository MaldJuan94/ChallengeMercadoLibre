package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.utils.SharedPreferenceUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class GetLastSearchedItemsUseCase @Inject constructor(
    private val sharedPreferenceUtils: SharedPreferenceUtils,
    @ApplicationContext private val context: Context
) {
    fun invoke(): ArrayList<String> {
        return sharedPreferenceUtils.getLastSearchedListNewMyDiscount(context)
    }
}