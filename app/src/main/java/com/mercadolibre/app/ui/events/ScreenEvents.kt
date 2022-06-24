package com.mercadolibre.app.ui.events

sealed class ScreenEvents {
    object GoBack : ScreenEvents()
    class GoToResult(val query: String) : ScreenEvents()
    class AutoCompleteSearch(val query: String) : ScreenEvents()
}
