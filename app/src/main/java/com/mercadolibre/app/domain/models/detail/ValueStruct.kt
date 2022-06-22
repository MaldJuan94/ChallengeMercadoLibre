package com.mercadolibre.app.domain.models.detail

import com.google.gson.annotations.SerializedName

data class ValueStruct(
    @SerializedName("number") var number: Float? = null,
    @SerializedName("unit") var unit: String? = null
)
