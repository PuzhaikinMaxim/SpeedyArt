package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.model.DifficultyDbModel
import com.mxpj.speedyart.data.database.model.PictureCompletionDbModel
import com.mxpj.speedyart.data.database.queryresult.CompletionWithDifficulty
import com.mxpj.speedyart.domain.model.DifficultyLevel
import com.mxpj.speedyart.domain.model.LevelEasy
import com.mxpj.speedyart.domain.model.LevelHard
import com.mxpj.speedyart.domain.model.LevelMedium
import com.mxpj.speedyart.presentation.DifficultyStatus
import javax.inject.Inject

class DifficultyLevelMapper @Inject constructor() {

    fun mapCompletionWithDifficultyListToDifficultyLevelList(
        completionWithDifficultyList: List<CompletionWithDifficulty>
    ): List<DifficultyLevel> {
        return completionWithDifficultyList.map { mapCompletionWithDifficultyToDifficultyLevel(it) }
    }

    fun mapCompletionWithDifficultyToDifficultyLevel(
        completionWithDifficulty: CompletionWithDifficulty
    ): DifficultyLevel {
        val status = getStatus(completionWithDifficulty.pictureCompletionDbModel)
        return getDifficulty(completionWithDifficulty.difficultyDbModel.name, status)
    }

    private fun getDifficulty(name: String, status: DifficultyStatus): DifficultyLevel {
        return when(name) {
            DifficultyDbModel.DIFFICULTY_EASY -> {
                LevelEasy(status)
            }
            DifficultyDbModel.DIFFICULTY_MEDIUM -> {
                LevelMedium(status)
            }
            DifficultyDbModel.DIFFICULTY_HARD -> {
                LevelHard(status)
            }
            else -> throw RuntimeException("Difficulty name does not exist")
        }
    }

    private fun getStatus(pictureCompletionDbModel: PictureCompletionDbModel): DifficultyStatus {
        return completionStatuses[
                pictureCompletionDbModel.completionStatus
        ] ?: throw RuntimeException("Status not exist")
    }

    companion object {
        private val completionStatuses = mapOf(
            PictureCompletionDbModel.STATUS_LOCKED to DifficultyStatus.LOCKED,
            PictureCompletionDbModel.STATUS_UNLOCKED to DifficultyStatus.UNLOCKED,
            PictureCompletionDbModel.STATUS_COMPLETED to DifficultyStatus.COMPLETED,
            PictureCompletionDbModel.STATUS_PERFECT to DifficultyStatus.PERFECT
        )
    }
}