package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.models.error.CodeError
import com.mercadolibre.app.models.search.SearchProductsResponse
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.NetworkUtilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetSearchProductByCategoryUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(
        limit: Int,
        offset: Int,
        category: String,
        listener: GetSearchProductByCategoryUseCaseListener
    ): SearchProductsResponse? {
        if (!Network.checkNetworkStateWithNetworkCapabilities(context)) {
            listener.onFailureGetSearchProductByCategory(NetworkUtilities.notInternetConnectionSharedCodeError)
            return null
        }
        if (category.isNotEmpty()) {
            val result = repository.searchProductsByCategory(
                limit = limit,
                offset = offset,
                category = category
            )
            if (result.errorMessage == null && result.data != null) {
                return result.data
            } else {
                listener.onFailureGetSearchProductByCategory(
                    CodeError(
                        "error_services",
                        result.errorMessage.orEmpty()
                    )
                )
            }
        } else {
            listener.onFailureGetSearchProductByCategory(CodeError("empty", ""))
        }
        return null
    }

    interface GetSearchProductByCategoryUseCaseListener {
        fun onFailureGetSearchProductByCategory(errors: CodeError)
    }
}