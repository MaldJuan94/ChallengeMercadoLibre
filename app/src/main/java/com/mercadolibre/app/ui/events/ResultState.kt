package com.mercadolibre.app.ui.events

import com.mercadolibre.app.models.search.Paging
import com.mercadolibre.app.models.search.Results
import com.mercadolibre.app.utils.SearchType

data class ResultState(
    var type: SearchType = SearchType.BY_CATEGORY,
    var query: String = "",
    var paging: Paging = Paging(),
    val loading: Boolean = false,
    val data: List<Results> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)