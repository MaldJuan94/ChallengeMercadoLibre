package com.mercadolibre.app.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.mercadolibre.app.R

object Fonts {
    val proximeNova = FontFamily(
        //Regular
        Font(R.font.proxima_nova_regular),
        Font(R.font.proxima_nova_regular_italic, style = FontStyle.Italic),
        //Bold
        Font(R.font.proxima_nova_bold, weight = FontWeight.Bold),
        Font(
            R.font.proxima_nova_bold_italic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
        //Extra bold
        Font(R.font.proxima_nova_extrabld, weight = FontWeight.ExtraBold),
        Font(
            R.font.proxima_nova_extrabld_italic,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        ),
        //Semi bold
        Font(R.font.proxima_nova_semibold, weight = FontWeight.SemiBold),
        Font(
            R.font.proxima_nova_semibold_italic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        ),
        //Black
        Font(R.font.proxima_nova_black, weight = FontWeight.Black),
        Font(
            R.font.proxima_nova_black_italic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
        //Light
        Font(R.font.proxima_nova_light, weight = FontWeight.Light),
        Font(
            R.font.proxima_nova_light_italic,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),
        //Thin weight 100
        Font(R.font.proxima_nova_thin, weight = FontWeight.Thin),
        Font(
            R.font.proxima_nova_thin_italic,
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        ),
    )
}
