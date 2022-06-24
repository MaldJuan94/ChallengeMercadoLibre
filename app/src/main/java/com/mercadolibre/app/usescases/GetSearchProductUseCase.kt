package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.models.error.CodeError
import com.mercadolibre.app.models.search.SearchProductsResponse
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.NetworkUtilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetSearchProductUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(
        limit: Int,
        offset: Int,
        query: String,
        listener: GetSearchProductUseCaseListener
    ): SearchProductsResponse? {
        if (!Network.checkNetworkStateWithNetworkCapabilities(context)) {
            listener.onFailureGetSearchProduct(NetworkUtilities.notInternetConnectionSharedCodeError)
            return null
        }
        if (query.isNotEmpty()) {
            val result = repository.searchProduct(
                limit = limit,
                offset = offset,
                query = query
            )
            if (result.errorMessage == null && result.data != null) {
                return result.data
            } else {
                listener.onFailureGetSearchProduct(
                    CodeError(
                        "error_services",
                        result.errorMessage.orEmpty()
                    )
                )
            }
        } else {
            listener.onFailureGetSearchProduct(CodeError("empty", ""))
        }
        return null
    }

    interface GetSearchProductUseCaseListener {
        fun onFailureGetSearchProduct(errors: CodeError)
    }
}