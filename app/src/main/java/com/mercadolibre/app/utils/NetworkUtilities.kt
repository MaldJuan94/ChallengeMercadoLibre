package com.mercadolibre.app.utils

import com.mercadolibre.app.models.error.CodeError

object NetworkUtilities {
    const val NOT_NETWORK_ERROR_CODE = "not network"
    val notInternetConnectionSharedCodeError = CodeError(code = NOT_NETWORK_ERROR_CODE, message = null)
}