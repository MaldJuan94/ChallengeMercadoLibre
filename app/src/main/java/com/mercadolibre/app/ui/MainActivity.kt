package com.mercadolibre.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.view.WindowCompat
import com.mercadolibre.app.ui.navigation.Navigation
import com.mercadolibre.app.viewmodel.RootViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val viewModel: RootViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            BaseApp {
                Navigation(
                    viewModel = viewModel
                )
            }
        }
    }
}
