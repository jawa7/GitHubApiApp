package com.githubapp.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalAnimationApi
@Composable
fun SlideLeftAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        modifier = Modifier.background(Color.Black),
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 200)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 200)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { 0 },
            animationSpec = spring(stiffness = Spring.StiffnessHigh)
        ) + fadeOut(),
        content = content,
        initiallyVisible = false
    )
}