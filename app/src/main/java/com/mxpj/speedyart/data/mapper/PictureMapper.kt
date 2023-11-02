package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.queryresult.PictureWithDifficulties
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.model.PictureWithStatistics
import javax.inject.Inject

class PictureMapper @Inject constructor(
    private val difficultyLevelMapper: DifficultyLevelMapper
) {

    fun mapPictureWithDifficultiesListToPictureList(
        pictureWithDifficultiesList: List<PictureWithDifficulties>
    ): List<Picture> {
        return pictureWithDifficultiesList.map { mapPictureWithDifficultiesToPicture(it) }
    }

    fun mapPictureWithDifficultiesToPictureWithStatistics(
        pictureWithDifficulties: PictureWithDifficulties
    ): PictureWithStatistics {
        return PictureWithStatistics(
            mapPictureWithDifficultiesToPicture(pictureWithDifficulties),
            getTimePairRepresentation(pictureWithDifficulties.picture.bestTime),
        )
    }

    fun mapPictureWithDifficultiesToPicture(
        pictureWithDifficulties: PictureWithDifficulties
    ): Picture {
        return Picture(
            pictureWithDifficulties.picture.id,
            pictureWithDifficulties.picture.assetLink.toInt(),
            difficultyLevelMapper.mapCompletionWithDifficultyListToDifficultyLevelList(
                pictureWithDifficulties.completionWithDifficultyList
            )
        )
    }

    private fun getTimePairRepresentation(time: Int?): Pair<Int, Int>? {
        if(time == null) return null
        val minutes = time / SECONDS_IN_MINUTE
        val seconds = time % SECONDS_IN_MINUTE
        return Pair(minutes, seconds)
    }

    companion object {
        private const val SECONDS_IN_MINUTE = 60
    }
}