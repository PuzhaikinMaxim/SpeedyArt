package com.mxpj.speedyart.domain.model

import com.mxpj.speedyart.presentation.DifficultyStatus
import com.mxpj.speedyart.presentation.toIntInPercent

data class PictureCompletion(
    val pictureAsset: Int,
    val difficulties: List<DifficultyLevel>
) {
    val completionPercent: Int
        get() {
            val maxProgress = difficulties.size * DIFFICULTY_PROGRESS_MULTIPLIER
            val perfectProgress = difficulties.count {
                it.status == DifficultyStatus.PERFECT
            } * PERFECT_WEIGHT
            val completedProgress = difficulties.count {
                it.status == DifficultyStatus.COMPLETED
            }
            val totalProgress = (perfectProgress.toFloat() + completedProgress)/maxProgress
            return totalProgress.toIntInPercent()
        }

    companion object {
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
        private const val PERFECT_WEIGHT = 2
    }
}
