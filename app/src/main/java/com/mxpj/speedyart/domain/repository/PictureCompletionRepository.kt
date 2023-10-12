package com.mxpj.speedyart.domain.repository

import com.mxpj.speedyart.data.database.queryresult.CompletionWithDifficulty
import com.mxpj.speedyart.domain.model.Game

interface PictureCompletionRepository {

    suspend fun changePictureCompletion(
        game: Game,
        gameCompletionWithDifficulty: CompletionWithDifficulty
    )
}