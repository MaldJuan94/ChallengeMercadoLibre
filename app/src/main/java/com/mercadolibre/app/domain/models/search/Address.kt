package  com.mercadolibre.app.domain.models.search

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("state_name") var stateName: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("city_name") var cityName: String? = null
)
