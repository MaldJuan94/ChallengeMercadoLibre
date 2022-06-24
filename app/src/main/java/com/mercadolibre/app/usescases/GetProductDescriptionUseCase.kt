package com.mercadolibre.app.usescases

import android.content.Context
import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.error.CodeError
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.NetworkUtilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetProductDescriptionUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(
        idProduct: String,
        listener: GetProductDescriptionUseCaseListener
    ) {
        if (!Network.checkNetworkStateWithNetworkCapabilities(context)) {
            listener.onFailureGetProductDescription(NetworkUtilities.notInternetConnectionSharedCodeError)
            return
        }
        if (idProduct.isNotEmpty()) {
            val result = repository.getProductDescription(id = idProduct)
            if (result.errorMessage == null && result.data != null) {
                listener.onSuccessGetProductDescription(result.data)
            } else {
                listener.onFailureGetProductDescription(
                    CodeError(
                        "error_services",
                        result.errorMessage.orEmpty()
                    )
                )
            }
        } else {
            listener.onFailureGetProductDescription(CodeError("empty", ""))
        }
    }

    interface GetProductDescriptionUseCaseListener {
        fun onSuccessGetProductDescription(response: ProductDescription)
        fun onFailureGetProductDescription(errors: CodeError)
    }
}