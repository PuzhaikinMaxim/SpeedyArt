package com.mxpj.speedyart.data.repository

import com.mxpj.speedyart.data.database.dao.PictureDao
import com.mxpj.speedyart.data.mapper.PictureMapper
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.model.PictureWithStatistics
import com.mxpj.speedyart.domain.repository.PictureRepository
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val pictureDao: PictureDao,
    private val pictureMapper: PictureMapper
): PictureRepository {

    override suspend fun getPictureCompletionList(pack: String): List<Picture> {
        return pictureMapper.mapPictureWithDifficultiesListToPictureList(
            pictureDao.getPictureListWithDifficultyProgress(pack)
        )
    }

    override suspend fun getPictureStatistics(id: Int): PictureWithStatistics {
        return pictureMapper.mapPictureWithDifficultiesToPictureWithStatistics(
            pictureDao.getPictureWithDifficultyProgress(id)
        )
    }

    override suspend fun setNewBestTime(id: Int, newTime: Int) {
        var picture = pictureDao.getPictureById(id)
        if(newTime < (picture.bestTime ?: Int.MAX_VALUE)){
            picture = picture.copy(bestTime = newTime)
            pictureDao.updatePicture(picture)
        }
    }
}