package  com.mercadolibre.app.domain.models.search

import com.google.gson.annotations.SerializedName

data class ReferencePrices(
    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("conditions") var conditions: Conditions? = Conditions(),
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("currency_id") var currencyId: String? = null,
    @SerializedName("exchange_rate_context") var exchangeRateContext: String? = null,
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("last_updated") var lastUpdated: String? = null
)
