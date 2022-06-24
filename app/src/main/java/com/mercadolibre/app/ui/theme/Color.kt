package com.mercadolibre.app.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

/**
 * This is the minimum amount of calculated contrast for a color to be used on top of the
 * surface color. These values are defined within the WCAG AA guidelines, and we use a value of
 * 3:1 which is the minimum for user-interface components.
 */
const val MinContrastOfPrimaryVsSurface = 3f

/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

val Yellow200 = Color(0xFFFFF159)
val Yellow500 = Color(0xFF9C9C9C)
val Yellow100 = Color(0xFFFFF587)
val Blue100 = Color(0XFF3483FA)
val Red300 = Color(0xFFEA6D7E)
val GeneralWhite = Color(0xFFFFFFFF)
val GeneralBackGround = Color(0xFFF3F3F3)
val GeneralBlack = Color(0xFF333333)
val GrayOne = Color(0xFFDBDBDB)
val GrayTwo = Color(0xFF929292)
val GreenOne = Color(0xFF00a650)

val AppDarkColors = darkColors(
    primary = Yellow200,
    primaryVariant = Yellow100,
    secondary = Blue100,
    surface = Red300,
)

val AppLightColors = lightColors(
    primary = Yellow100,
    primaryVariant = Yellow100,
    secondary = Blue100,
    surface = Red300,
)
