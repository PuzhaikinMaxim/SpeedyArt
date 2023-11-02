package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.model.DifficultyDbModel
import com.mxpj.speedyart.data.database.model.CompletionDbModel
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
        val status = getStatus(completionWithDifficulty.completionDbModel)
        return getDifficulty(
            completionWithDifficulty.difficultyDbModel.name,
            status,
            completionWithDifficulty.completionDbModel.id
        )
    }

    fun mapCompletionToDifficultyLevel(
        completionDbModel: CompletionDbModel
    ): DifficultyLevel {
        val status = getStatus(completionDbModel)
        return getDifficulty(
            completionDbModel.difficulty,
            status,
            completionDbModel.id
        )
    }

    private fun getDifficulty(
        name: String,
        status: DifficultyStatus,
        completionId: Int
    ): DifficultyLevel {
        return when(name) {
            DifficultyDbModel.DIFFICULTY_EASY -> {
                LevelEasy(status, completionId)
            }
            DifficultyDbModel.DIFFICULTY_MEDIUM -> {
                LevelMedium(status, completionId)
            }
            DifficultyDbModel.DIFFICULTY_HARD -> {
                LevelHard(status, completionId)
            }
            else -> throw RuntimeException("Difficulty name does not exist")
        }
    }

    private fun getStatus(completionDbModel: CompletionDbModel): DifficultyStatus {
        return completionStatuses[
                completionDbModel.completionStatus
        ] ?: throw RuntimeException("Status not exists")
    }

    companion object {
        private val completionStatuses = mapOf(
            CompletionDbModel.STATUS_LOCKED to DifficultyStatus.LOCKED,
            CompletionDbModel.STATUS_UNLOCKED to DifficultyStatus.UNLOCKED,
            CompletionDbModel.STATUS_COMPLETED to DifficultyStatus.COMPLETED,
            CompletionDbModel.STATUS_PERFECT to DifficultyStatus.PERFECT
        )
    }
}