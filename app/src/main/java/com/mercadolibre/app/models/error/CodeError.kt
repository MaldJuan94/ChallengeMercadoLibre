package com.mercadolibre.app.models.error

import com.google.gson.annotations.SerializedName

data class CodeError(
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?
)