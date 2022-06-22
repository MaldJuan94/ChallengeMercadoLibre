package com.mercadolibre.app.repository

import com.mercadolibre.app.domain.models.description.ProductDescription
import com.mercadolibre.app.domain.models.detail.DetailResponse
import com.mercadolibre.app.domain.models.search.SearchProductsResponse
import com.mercadolibre.app.network.NetworkStatus
import com.mercadolibre.app.network.ApiService
import com.mercadolibre.app.network.safeApiCall

class RecipeRepositoryImpl(
    private val recipeService: ApiService
) : RecipeRepository {

    override suspend fun searchProduct(
        page: Int,
        query: String
    ): NetworkStatus<SearchProductsResponse> {
        return safeApiCall { recipeService.searchProduct(query = query) }
    }

    override suspend fun getProduct(id: String): NetworkStatus<DetailResponse> {
        return safeApiCall { recipeService.getProduct(id = id) }
    }

    override suspend fun getProductDescription(id: String): NetworkStatus<ProductDescription> {
        return safeApiCall { recipeService.getProductDescription(id = id) }
    }

}
