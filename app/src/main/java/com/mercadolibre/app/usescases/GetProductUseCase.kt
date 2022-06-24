package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.error.CodeError
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.NetworkUtilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(
        idProduct: String,
        listener: GetProductUseCaseListener
    ) {
        if (!Network.checkNetworkStateWithNetworkCapabilities(context)) {
            listener.onFailureGetProduct(NetworkUtilities.notInternetConnectionSharedCodeError)
            return
        }
        if (idProduct.isNotEmpty()) {
            val result = repository.getProduct(id = idProduct)
            if (result.errorMessage == null && result.data != null) {
                listener.onSuccessGetProduct(result.data)
            } else {
                listener.onFailureGetProduct(
                    CodeError(
                        "error_services",
                        result.errorMessage.orEmpty()
                    )
                )
            }
        } else {
            listener.onFailureGetProduct(CodeError("empty", ""))
        }
    }

    interface GetProductUseCaseListener {
        fun onSuccessGetProduct(response: DetailResponse)
        fun onFailureGetProduct(errors: CodeError)
    }
}