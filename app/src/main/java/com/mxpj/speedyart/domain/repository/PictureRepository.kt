package com.mxpj.speedyart.domain.repository

import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.model.PictureWithStatistics

interface PictureRepository {

    suspend fun getPictureCompletionList(pack: String): List<Picture>

    suspend fun getPictureStatistics(id: Int): PictureWithStatistics
}