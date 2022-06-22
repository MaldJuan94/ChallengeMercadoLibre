package com.mercadolibre.app.repository

import com.mercadolibre.app.domain.models.description.ProductDescription
import com.mercadolibre.app.domain.models.detail.DetailResponse
import com.mercadolibre.app.domain.models.search.Results
import com.mercadolibre.app.domain.models.search.SearchProductsResponse
import com.mercadolibre.app.network.NetworkStatus

interface RecipeRepository {
    suspend fun searchProduct(page: Int, query: String): NetworkStatus<SearchProductsResponse>
    suspend fun getProduct(id: String): NetworkStatus<DetailResponse>
    suspend fun getProductDescription(id: String): NetworkStatus<ProductDescription>
}