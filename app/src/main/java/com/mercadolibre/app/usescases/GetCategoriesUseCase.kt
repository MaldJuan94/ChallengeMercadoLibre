package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.error.CodeError
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.NetworkUtilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(
        listener: GetCategoriesCaseListener
    ) {
        if (!Network.checkNetworkStateWithNetworkCapabilities(context)) {
            listener.onFailureGetCategories(NetworkUtilities.notInternetConnectionSharedCodeError)
            return
        }
        val result = repository.getCategories()
        if (result.errorMessage == null && result.data != null) {
            listener.onSuccessGetCategories(result.data)
        } else {
            listener.onFailureGetCategories(
                CodeError(
                    "error_services",
                    result.errorMessage.orEmpty()
                )
            )
        }
    }

    interface GetCategoriesCaseListener {
        fun onSuccessGetCategories(response: ArrayList<Categories>)
        fun onFailureGetCategories(errors: CodeError)
    }
}