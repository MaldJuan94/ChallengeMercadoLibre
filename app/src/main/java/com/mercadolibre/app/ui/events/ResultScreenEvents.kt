package com.mercadolibre.app.ui.events

sealed class ResultScreenEvents {
    object GotoSearch : ResultScreenEvents()
    object GoBack : ResultScreenEvents()
    class GotoDetail(val idProduct: String, val titleProduct: String) : ResultScreenEvents()
}
