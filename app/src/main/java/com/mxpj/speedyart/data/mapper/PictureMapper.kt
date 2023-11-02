package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.queryresult.CompletionWithPicture
import com.mxpj.speedyart.data.database.queryresult.PictureWithDifficulties
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.model.PictureDifficulty
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

    fun mapCompletionWithPictureToPictureDifficulty(
        completionWithPicture: CompletionWithPicture
    ): PictureDifficulty {
        println("cid " + completionWithPicture.completion.id)
        return PictureDifficulty(
            completionWithPicture.picture.id,
            completionWithPicture.picture.assetLink,
            difficultyLevelMapper.mapCompletionToDifficultyLevel(completionWithPicture.completion)
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