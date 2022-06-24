package com.mercadolibre.app.ui.events

import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.search.Prices

data class ResultProductState(
    val loading: Boolean = true,
    val data: DetailResponse = DetailResponse(),
    val prices: Prices? = Prices(),
    val description: ProductDescription = ProductDescription(),
    val error: String? = null,
)