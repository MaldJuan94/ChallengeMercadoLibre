package com.mercadolibre.app.network

import com.mercadolibre.app.domain.models.description.ProductDescription
import com.mercadolibre.app.domain.models.detail.DetailResponse
import com.mercadolibre.app.domain.models.search.SearchProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sites/MCO/search")
    suspend fun searchProduct(
        @Query("q") query: String
    ): Response<SearchProductsResponse>

    @GET("items/{id}")
    suspend fun getProduct(
        @Path("id") id: String
    ): Response<DetailResponse>

    @GET("items/{id}/description")
    suspend fun getProductDescription(
        @Path("id") id: String
    ): Response<ProductDescription>

}
