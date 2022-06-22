package com.mercadolibre.app.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mercadolibre.app.R
import com.mercadolibre.app.ui.theme.GeneralWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    val scaleAnimation: Animatable<Float, AnimationVector1D> =
        remember { Animatable(initialValue = 0f) }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        durationMillisAnimation = 1500,
        onFinished = onFinished,
        delayScreen = 2000L
    )

    body(scaleAnimation)
}

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    durationMillisAnimation: Int,
    onFinished: () -> Unit,
    delayScreen: Long
) {
    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 1F,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(1F).getInterpolation(it)
                }
            )
        )
        delay(timeMillis = delayScreen)
        onFinished.invoke()
    }
}

@Composable
fun body(
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GeneralWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .scale(scale = scaleAnimation.value)
                .alpha(alpha = scaleAnimation.value),
            painter = painterResource(
                id = R.drawable.ic_mercadolibre
            ),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun previewSplash() {
    val scaleAnimation: Animatable<Float, AnimationVector1D> =
        remember { Animatable(initialValue = 1f) }
    body(scaleAnimation)
}