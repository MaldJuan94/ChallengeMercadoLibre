package com.mercadolibre.app.domain.models.detail

import com.google.gson.annotations.SerializedName

data class City (
  @SerializedName("id"   ) var id   : String? = null,
  @SerializedName("name" ) var name : String? = null
)
