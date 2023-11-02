package com.mxpj.speedyart.domain.repository

import com.mxpj.speedyart.domain.model.Game
import com.mxpj.speedyart.domain.model.PictureDifficulty
import com.mxpj.speedyart.domain.model.TotalCompletion

interface PictureCompletionRepository {

    suspend fun changePictureCompletion(game: Game)

    suspend fun getTotalCompletion(): TotalCompletion

    suspend fun getPictureDifficulty(completionId: Int): PictureDifficulty

    suspend fun resetProgress()
}