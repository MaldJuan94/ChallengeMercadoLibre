package com.mercadolibre.app.ui.events

import com.mercadolibre.app.models.categories.Categories

sealed class MainState {
    object Loading : MainState()
    object ErrorNetwork : MainState()
    class SuccessGetCategories(val categories: ArrayList<Categories>) : MainState()
    class Error(val message: String) : MainState()
}