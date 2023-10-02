package com.mxpj.speedyart.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.mxpj.speedyart.domain.AppTheme

@Composable
fun SpeedyArtTheme(
    theme: AppTheme,
    content: @Composable () -> Unit
) {
    val colors = when(theme){
        AppTheme.LIGHT -> {
            SpeedyArtColorsLight
        }
        AppTheme.DARK -> {
            SpeedyArtColorsDark
        }
    }
    CompositionLocalProvider(LocalSpeedyArtColors provides colors) {
        MaterialTheme(
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object SpeedyArtTheme {
    val colors: SpeedyArtColors
        @Composable
        get() = LocalSpeedyArtColors.current
}