package com.mxpj.speedyart.data.repository

import com.mxpj.speedyart.data.database.dao.PictureDao
import com.mxpj.speedyart.data.mapper.PictureMapper
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.model.PictureStatistics
import com.mxpj.speedyart.domain.repository.PictureRepository
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val pictureDao: PictureDao,
    private val pictureMapper: PictureMapper
): PictureRepository {

    override suspend fun getPictureCompletionList(pack: String): List<PictureCompletion> {
        return pictureMapper.mapPictureWithCompletionListToPictureCompletionList(
            pictureDao.getPictureListWithDifficultyProgress(pack)
        )
    }

    override suspend fun getPictureStatistics(id: Int): PictureStatistics {
        return pictureMapper.mapPictureWithCompletionToPictureStatistics(
            pictureDao.getPictureWithDifficultyProgress(id)
        )
    }
}