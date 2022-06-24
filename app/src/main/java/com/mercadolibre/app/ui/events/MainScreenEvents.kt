package com.mercadolibre.app.ui.events

import com.mercadolibre.app.models.categories.Categories

sealed class MainScreenEvents {
    object GotoBack : MainScreenEvents()
    object GotoSearch : MainScreenEvents()
    class GoToCategory(val category: Categories) : MainScreenEvents()
}
