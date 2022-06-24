package com.mercadolibre.app.repository

import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.search.SearchProductsResponse
import com.mercadolibre.app.network.NetworkStatus

interface RecipeRepository {
    suspend fun searchProduct(
        limit: Int,
        offset: Int,
        query: String
    ): NetworkStatus<SearchProductsResponse>

    suspend fun searchProductsByCategory(
        limit: Int,
        offset: Int,
        category: String
    ): NetworkStatus<SearchProductsResponse>

    suspend fun getProduct(id: String): NetworkStatus<DetailResponse>
    suspend fun getProductDescription(id: String): NetworkStatus<ProductDescription>
    suspend fun getCategories(): NetworkStatus<ArrayList<Categories>>

}