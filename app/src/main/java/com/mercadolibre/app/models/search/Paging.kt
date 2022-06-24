package  com.mercadolibre.app.models.search

import com.google.gson.annotations.SerializedName

data class Paging(
    @SerializedName("total") var total: Int = 0,
    @SerializedName("primary_results") var primaryResults: Int = 0,
    @SerializedName("offset") var offset: Int = 0,
    @SerializedName("limit") var limit: Int = 0
)
