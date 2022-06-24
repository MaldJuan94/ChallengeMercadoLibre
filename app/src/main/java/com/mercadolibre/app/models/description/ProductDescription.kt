package com.mercadolibre.app.models.description

import com.google.gson.annotations.SerializedName

data class ProductDescription(
    @SerializedName("text") var text: String? = null,
    @SerializedName("plain_text") var plainText: String? = null,
    @SerializedName("last_updated") var lastUpdated: String? = null,
    @SerializedName("date_created") var dateCreated: String? = null,
)
