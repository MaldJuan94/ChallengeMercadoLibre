package com.mercadolibre.app.models.detail

import com.google.gson.annotations.SerializedName

data class Pictures(
    @SerializedName("id") var id: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("secure_url") var secureUrl: String? = null,
    @SerializedName("size") var size: String? = null,
    @SerializedName("max_size") var maxSize: String? = null,
    @SerializedName("quality") var quality: String? = null
)
