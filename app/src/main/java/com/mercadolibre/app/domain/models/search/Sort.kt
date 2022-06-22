package  com.mercadolibre.app.domain.models.search

import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)
