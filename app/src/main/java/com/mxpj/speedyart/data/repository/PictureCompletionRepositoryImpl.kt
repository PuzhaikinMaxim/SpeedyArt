package com.mxpj.speedyart.data.repository

import com.mxpj.speedyart.data.database.dao.PictureCompletionDao
import com.mxpj.speedyart.data.database.model.CompletionDbModel
import com.mxpj.speedyart.data.database.queryresult.CompletionWithDifficulty
import com.mxpj.speedyart.data.database.queryresult.TotalCompletionQueryResult
import com.mxpj.speedyart.data.mapper.TotalCompletionMapper
import com.mxpj.speedyart.domain.model.Game
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.TotalCompletion
import com.mxpj.speedyart.domain.repository.PictureCompletionRepository
import javax.inject.Inject

class PictureCompletionRepositoryImpl @Inject constructor(
    private val pictureCompletionDao: PictureCompletionDao,
    private val totalCompletionMapper: TotalCompletionMapper
): PictureCompletionRepository {

    override suspend fun changePictureCompletion(
        game: Game,
        gameCompletionWithDifficulty: CompletionWithDifficulty
    ) {
        if(game.result == GameResult.GAME_LOST || game.result == GameResult.GAME_CONTINUING) return
        val status: String
        with(CompletionDbModel.Companion){
            status = if(game.amountOfMistakes == 0) STATUS_PERFECT else STATUS_COMPLETED
        }
        val pictureCompletionWithNewStatus = gameCompletionWithDifficulty.completionDbModel.copy(
            completionStatus = status
        )
        pictureCompletionDao.updatePictureCompletion(pictureCompletionWithNewStatus)
        pictureCompletionDao.unlockNextDifficulty(
            game.imageId, gameCompletionWithDifficulty.difficultyDbModel.unlocking
        )
    }

    override suspend fun getTotalCompletion(): TotalCompletion {
        return totalCompletionMapper.mapTotalCompletionQueryResultToTotalCompletion(
            pictureCompletionDao.getTotalCompletion()
        )
    }

    override suspend fun resetProgress() {
        pictureCompletionDao.clearCompletion()
    }
}