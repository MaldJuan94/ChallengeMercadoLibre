package com.mercadolibre.app.repository

import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.search.SearchProductsResponse
import com.mercadolibre.app.network.NetworkStatus
import com.mercadolibre.app.network.ApiService
import com.mercadolibre.app.network.safeApiCall

class RecipeRepositoryImpl(
    private val recipeService: ApiService
) : RecipeRepository {

    override suspend fun searchProduct(
        limit: Int,
        offset: Int,
        query: String
    ): NetworkStatus<SearchProductsResponse> {
        return safeApiCall {
            recipeService.searchProduct(
                query = query,
                limit = limit,
                offset = offset
            )
        }
    }

    override suspend fun searchProductsByCategory(
        limit: Int,
        offset: Int,
        category: String
    ): NetworkStatus<SearchProductsResponse> {
        return safeApiCall {
            recipeService.searchProductsByCategory(
                category = category,
                limit = limit,
                offset = offset
            )
        }
    }

    override suspend fun getProduct(id: String): NetworkStatus<DetailResponse> {
        return safeApiCall { recipeService.getProduct(id = id) }
    }

    override suspend fun getProductDescription(id: String): NetworkStatus<ProductDescription> {
        return safeApiCall { recipeService.getProductDescription(id = id) }
    }

    override suspend fun getCategories(): NetworkStatus<ArrayList<Categories>> {
        return safeApiCall { recipeService.getCategories() }
    }

}
