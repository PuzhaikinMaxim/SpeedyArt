package com.mxpj.speedyart.domain.repository

import com.mxpj.speedyart.domain.model.DifficultyLevel
import com.mxpj.speedyart.domain.model.DifficultySettings

interface DifficultySettingsRepository {

    fun getDifficultySettings(
        difficultyLevel: DifficultyLevel,
        size: Pair<Int,Int>
    ): DifficultySettings
}