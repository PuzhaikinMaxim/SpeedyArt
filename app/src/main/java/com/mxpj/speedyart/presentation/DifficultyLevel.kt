package com.mxpj.speedyart.presentation

import androidx.compose.ui.graphics.Color
import com.mxpj.speedyart.R
import com.mxpj.speedyart.ui.theme.DifficultyGreen
import com.mxpj.speedyart.ui.theme.DifficultyRed
import com.mxpj.speedyart.ui.theme.DifficultyYellow

enum class DifficultyLevel(val color: Color, val textResource: Int) {
    EASY(DifficultyGreen, R.string.difficulty_easy),
    MEDIUM(DifficultyYellow, R.string.difficulty_medium),
    HARD(DifficultyRed, R.string.diffoculty_hard)
}