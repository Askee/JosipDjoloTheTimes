package com.josipdjolo.thetimes.core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

const val ANIMATION_DURATION_MILLIS = 1300
const val BRUSH_WIDTH = 200f

val LocalShimmerBrush = staticCompositionLocalOf<Brush> {
    error("LocalShimmerBrush not present")
}

@Composable
fun Modifier.shimmer() = this.background(LocalShimmerBrush.current)

@Composable
fun ShimmerProvider(content: @Composable () -> Unit) {
    val transition = rememberInfiniteTransition(label = "shimmer")

    val translate by transition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ANIMATION_DURATION_MILLIS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.White.copy(0.5f),
            Color.LightGray.copy(alpha = 0.5f)
        ),
        start = Offset(translate, translate),
        end = Offset(translate + BRUSH_WIDTH, translate + BRUSH_WIDTH)
    )
    CompositionLocalProvider(LocalShimmerBrush provides brush) {
        content()
    }

}