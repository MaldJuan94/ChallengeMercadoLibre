package com.mercadolibre.app.ui.utils

interface Pager<Key, Model> {
    suspend fun onLoadNextData(query: String, type: String)
    fun onReset()
}