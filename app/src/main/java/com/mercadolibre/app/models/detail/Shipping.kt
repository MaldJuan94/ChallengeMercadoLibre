package com.mercadolibre.app.models.detail

import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("mode") var mode: String? = null,
    @SerializedName("methods") var methods: ArrayList<String> = arrayListOf(),
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("dimensions") var dimensions: String? = null,
    @SerializedName("local_pick_up") var localPickUp: Boolean? = null,
    @SerializedName("free_shipping") var freeShipping: Boolean? = null,
    @SerializedName("logistic_type") var logisticType: String? = null,
    @SerializedName("store_pick_up") var storePickUp: Boolean? = null
)
