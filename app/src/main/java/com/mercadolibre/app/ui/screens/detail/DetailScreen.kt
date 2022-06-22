package com.mercadolibre.app.ui.screens.detail

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.mercadolibre.app.ui.viewmodel.RootViewModel

@Composable
fun DetailScreen(
    viewModel: RootViewModel,
    productId: String,
    onUpClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Text $productId") },
                navigationIcon = { }
            )
        }
    ) {
    }
}