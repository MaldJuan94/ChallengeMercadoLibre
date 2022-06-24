package com.mercadolibre.app.models.detail

import com.google.gson.annotations.SerializedName

data class Values(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("struct") var struct: Struct? = Struct()
)
