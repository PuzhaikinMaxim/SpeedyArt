package com.mxpj.speedyart.domain

import com.mxpj.speedyart.presentation.Difficulty
import com.mxpj.speedyart.presentation.toIntInPercent

data class PictureCompletion(
    val pictureResource: Int,
    val difficulties: List<Difficulty>
) {
    val completionPercent: Int
        get() {
            val maxProgress = difficulties.size * DIFFICULTY_PROGRESS_MULTIPLIER
            val perfectProgress = difficulties.count {
                it is Difficulty.DifficultyPerfect
            } * PERFECT_WEIGHT
            val completedProgress = difficulties.count { it is Difficulty.DifficultyCompleted }
            val totalProgress = (perfectProgress.toFloat() + completedProgress)/maxProgress
            return totalProgress.toIntInPercent()
        }

    companion object {
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
        private const val PERFECT_WEIGHT = 2
    }
}
