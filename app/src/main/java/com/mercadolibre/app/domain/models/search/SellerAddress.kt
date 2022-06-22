package  com.mercadolibre.app.domain.models.search

import com.google.gson.annotations.SerializedName

data class SellerAddress(
    @SerializedName("id") var id: String? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("address_line") var addressLine: String? = null,
    @SerializedName("zip_code") var zipCode: String? = null,
    @SerializedName("country") var country: Country? = Country(),
    @SerializedName("state") var state: State? = State(),
    @SerializedName("city") var city: City? = City(),
    @SerializedName("latitude") var latitude: String? = null,
    @SerializedName("longitude") var longitude: String? = null
)
