package com.mxpj.speedyart.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.mxpj.speedyart.domain.model.AppTheme

val LocalTheme = staticCompositionLocalOf { AppTheme.LIGHT }

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
    CompositionLocalProvider(
        LocalSpeedyArtColors provides colors,
        LocalTheme provides theme
    ) {
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
    val theme: AppTheme
        @Composable
        get() = LocalTheme.current
}