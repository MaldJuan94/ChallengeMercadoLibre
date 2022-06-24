package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.network.MAXIMUM_SIZE_LAST_SEARCHED
import com.mercadolibre.app.utils.SharedPreferenceUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class SaveKeyWordInSearchHistoryUseCase @Inject constructor(
    private val sharedPreferenceUtils: SharedPreferenceUtils,
    @ApplicationContext private val context: Context
) {
    fun invoke(keyword: String?) {
        val keywordSelected = keyword.orEmpty().lowercase(Locale.ROOT)
        val lastItemsList = sharedPreferenceUtils.getLastSearchedListNewMyDiscount(context)
        if (!lastItemsList.contains(keywordSelected)) {
            if (lastItemsList.size >= MAXIMUM_SIZE_LAST_SEARCHED) {
                Collections.rotate(lastItemsList, 1)
                lastItemsList[0] = keywordSelected
            } else {
                lastItemsList.add(0, keywordSelected)
            }
        } else {
            val index: Int = lastItemsList.indexOf(keywordSelected)
            lastItemsList.removeAt(index)
            lastItemsList.add(0, keywordSelected)
        }
        sharedPreferenceUtils.saveLastSearchedListNewMyDiscount(context, lastItemsList)
    }
}