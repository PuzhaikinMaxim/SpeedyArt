package com.mxpj.speedyart.data.repository

import com.mxpj.speedyart.data.database.dao.PictureCompletionDao
import com.mxpj.speedyart.data.database.model.CompletionDbModel
import com.mxpj.speedyart.data.mapper.PictureMapper
import com.mxpj.speedyart.data.mapper.TotalCompletionMapper
import com.mxpj.speedyart.domain.model.Game
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.PictureDifficulty
import com.mxpj.speedyart.domain.model.TotalCompletion
import com.mxpj.speedyart.domain.repository.PictureCompletionRepository
import com.mxpj.speedyart.domain.repository.PictureRepository
import javax.inject.Inject

class PictureCompletionRepositoryImpl @Inject constructor(
    private val pictureCompletionDao: PictureCompletionDao,
    private val totalCompletionMapper: TotalCompletionMapper,
    private val pictureRepository: PictureRepository,
    private val pictureMapper: PictureMapper
): PictureCompletionRepository {

    override suspend fun changePictureCompletion(
        game: Game
    ) {
        if(game.result == GameResult.GAME_LOST || game.result == GameResult.GAME_CONTINUING) return
        val status: String
        with(CompletionDbModel.Companion){
            status = if(game.amountOfMistakes == 0) STATUS_PERFECT else STATUS_COMPLETED
        }
        val pictureCompletionWithDifficulty = pictureCompletionDao.getPictureCompletionWithDifficulty(
            game.pictureDifficulty.difficultyLevel.completionId
        )
        val pictureCompletionWithNewStatus = pictureCompletionWithDifficulty.completionDbModel.copy(
            completionStatus = CompletionDbModel.getStatus(
                status,
                pictureCompletionWithDifficulty.completionDbModel.completionStatus
            )
        )
        pictureCompletionDao.updatePictureCompletion(pictureCompletionWithNewStatus)
        pictureCompletionDao.unlockNextDifficulty(
            game.pictureDifficulty.id, pictureCompletionWithDifficulty.difficultyDbModel.unlocking
        )
        pictureRepository.setNewBestTime(game.pictureDifficulty.id, game.time)
    }

    override suspend fun getTotalCompletion(): TotalCompletion {
        return totalCompletionMapper.mapTotalCompletionQueryResultToTotalCompletion(
            pictureCompletionDao.getTotalCompletion()
        )
    }

    override suspend fun getPictureDifficulty(completionId: Int): PictureDifficulty {
        return pictureMapper.mapCompletionWithPictureToPictureDifficulty(
            pictureCompletionDao.getCompletionWithPicture(completionId)
        )
    }

    override suspend fun resetProgress() {
        pictureCompletionDao.clearCompletion()
    }
}