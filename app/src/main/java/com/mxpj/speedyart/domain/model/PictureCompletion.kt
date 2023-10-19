package com.mxpj.speedyart.domain.model

import com.mxpj.speedyart.presentation.DifficultyStatus

data class PictureCompletion(
    val id: Int,
    val pictureAsset: Int,
    val difficulties: List<DifficultyLevel>
) {
    val completionPercent: Float
        get() {
            val maxProgress = difficulties.size * DIFFICULTY_PROGRESS_MULTIPLIER
            val perfectProgress = difficulties.count {
                it.status == DifficultyStatus.PERFECT
            }
            val completedProgress = difficulties.count {
                it.status == DifficultyStatus.COMPLETED
            }
            return (perfectProgress.toFloat() + completedProgress) / maxProgress
        }

    companion object {
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
    }
}
