package com.mxpj.speedyart.domain.repository

import com.mxpj.speedyart.data.database.queryresult.CompletionWithDifficulty
import com.mxpj.speedyart.data.database.queryresult.TotalCompletionQueryResult
import com.mxpj.speedyart.domain.model.Game
import com.mxpj.speedyart.domain.model.TotalCompletion

interface PictureCompletionRepository {

    suspend fun changePictureCompletion(game: Game, gameCompletionWithDifficulty: CompletionWithDifficulty)

    suspend fun getTotalCompletion(): TotalCompletion

    suspend fun resetProgress()
}