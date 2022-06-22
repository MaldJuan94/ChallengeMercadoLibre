package com.mercadolibre.app.domain.models.detail

import com.google.gson.annotations.SerializedName

data class SaleTerms(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("value_id") var valueId: String? = null,
    @SerializedName("value_name") var valueName: String? = null,
    @SerializedName("value_struct") var valueStruct: ValueStruct? = ValueStruct(),
    @SerializedName("values") var values: ArrayList<Values> = arrayListOf()
)
