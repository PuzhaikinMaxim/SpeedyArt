package com.mxpj.speedyart.domain.repository

import androidx.lifecycle.LiveData
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.model.PictureStatistics

interface PictureRepository {

    suspend fun getPictureCompletionList(pack: String): List<PictureCompletion>

    suspend fun getPictureStatistics(id: Int): PictureStatistics
}