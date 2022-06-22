package  com.mercadolibre.app.domain.models.search

import com.google.gson.annotations.SerializedName

data class Presentation(
    @SerializedName("display_currency") var displayCurrency: String? = null
)
