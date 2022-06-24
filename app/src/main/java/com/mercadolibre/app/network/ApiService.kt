package com.mercadolibre.app.network

import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.search.SearchProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sites/MCO/search")
    suspend fun searchProduct(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<SearchProductsResponse>

    @GET("sites/MCO/search")
    suspend fun searchProductsByCategory(
        @Query("category") category: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<SearchProductsResponse>

    @GET("items/{id}")
    suspend fun getProduct(
        @Path("id") id: String
    ): Response<DetailResponse>

    @GET("items/{id}/description")
    suspend fun getProductDescription(
        @Path("id") id: String
    ): Response<ProductDescription>

    @GET("sites/MCO/categories")
    suspend fun getCategories(): Response<ArrayList<Categories>>

}
