package com.mxpj.speedyart.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.staticCompositionLocalOf
import javax.annotation.concurrent.Immutable

@Immutable
data class SpeedyArtColors(
    val background: Color,
    val progressBar: Color,
    val text: Color,
    val topBar: Color,
    val primary: Color,
    val onPrimary: Color
)

val SpeedyArtColorsLight = SpeedyArtColors(
    background = White,
    progressBar = ProgressYellow,
    text = Black,
    topBar = Gray,
    primary = LightGray,
    onPrimary = Gray
)

val SpeedyArtColorsDark = SpeedyArtColors(
    background = DarkThemeBackground,
    progressBar = ProgressYellow,
    text = White,
    topBar = Gray,
    primary = DarkThemePrimary,
    onPrimary = DarkThemeOnPrimary
)

val LocalSpeedyArtColors = staticCompositionLocalOf { SpeedyArtColorsDark }

