package com.mercadolibre.app.models.categories

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)