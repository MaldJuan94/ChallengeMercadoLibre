package com.mercadolibre.app.domain.models.detail

import com.google.gson.annotations.SerializedName

data class SellerAddress(
    @SerializedName("city") var city: City? = City(),
    @SerializedName("state") var state: State? = State(),
    @SerializedName("country") var country: Country? = Country(),
    @SerializedName("search_location") var searchLocation: SearchLocation? = SearchLocation(),
    @SerializedName("id") var id: Int? = null
)
