package com.mercadolibre.app.models.detail

import com.google.gson.annotations.SerializedName

data class SearchLocation(
    @SerializedName("city") var city: City? = City(),
    @SerializedName("state") var state: State? = State()
)
