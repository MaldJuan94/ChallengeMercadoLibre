package com.mercadolibre.app.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.mercadolibre.app.ui.theme.AppTheme
import com.mercadolibre.app.ui.theme.GeneralWhite

@Composable
fun BaseApp(content: @Composable () -> Unit) {
    AppTheme {
        Surface(color = GeneralWhite) {
            content()
        }
    }
}