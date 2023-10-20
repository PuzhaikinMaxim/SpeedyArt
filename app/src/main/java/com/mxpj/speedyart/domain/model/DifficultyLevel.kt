package com.mxpj.speedyart.domain.model

import androidx.compose.ui.graphics.Color
import com.mxpj.speedyart.R
import com.mxpj.speedyart.presentation.DifficultyStatus
import com.mxpj.speedyart.ui.theme.DifficultyGreen
import com.mxpj.speedyart.ui.theme.DifficultyRed
import com.mxpj.speedyart.ui.theme.DifficultyYellow

sealed class DifficultyLevel(
    val color: Color,
    val textResource: Int,
    val status: DifficultyStatus
    )

class LevelEasy(status: DifficultyStatus): DifficultyLevel(
    DifficultyGreen, R.string.difficulty_easy,status
)

class LevelMedium(status: DifficultyStatus): DifficultyLevel(
    DifficultyYellow, R.string.difficulty_medium,status
)

class LevelHard(status: DifficultyStatus): DifficultyLevel(
    DifficultyRed, R.string.difficulty_hard,status
)