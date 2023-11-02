package com.mxpj.speedyart.domain.model

import com.mxpj.speedyart.presentation.DifficultyStatus

data class Picture(
    val id: Int,
    val pictureAsset: Int,
    val difficulties: List<DifficultyLevel>
) {
    val completionPercent: Float
        get() {
            val maxProgress = difficulties.size * DIFFICULTY_PROGRESS_MULTIPLIER
            val perfectProgress = difficulties.count {
                it.status == DifficultyStatus.PERFECT
            } * PERFECT_WEIGHT
            val completedProgress = difficulties.count {
                it.status == DifficultyStatus.COMPLETED
            }
            return (perfectProgress.toFloat() + completedProgress) / maxProgress
        }

    companion object {
        private const val PERFECT_WEIGHT = 2
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
    }
}
