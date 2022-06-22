package com.mercadolibre.app.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) AppDarkColors else AppLightColors
    val view = LocalView.current
    val context = LocalContext.current
    SideEffect {
        (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
        WindowCompat.getInsetsController(context.findActivity().window, view)
            .isAppearanceLightStatusBars = !darkTheme
    }
    MaterialTheme(
        colors = colorScheme,
        content = content
    )
}

private tailrec fun Context.findActivity(): Activity =
    when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }