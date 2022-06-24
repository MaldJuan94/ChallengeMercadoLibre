package com.mercadolibre.app.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.mercadolibre.app.R

@Composable
fun LoaderSearch(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search_loader))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = progress
    )
}