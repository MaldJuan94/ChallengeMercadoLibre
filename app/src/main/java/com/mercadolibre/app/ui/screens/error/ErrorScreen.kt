package com.mercadolibre.app.ui.screens.error

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mercadolibre.app.R
import com.mercadolibre.app.ui.theme.GeneralBlack
import com.mercadolibre.app.ui.theme.proximeNovaSemiBold

@Composable
fun ErrorScreen(message: String, modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_general))
    val progress by animateLottieCompositionAsState(composition)
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(50.dp)
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally),
            composition = composition,
            progress = progress
        )
        Text(
            text = message,
            style = proximeNovaSemiBold,
            color = GeneralBlack,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}