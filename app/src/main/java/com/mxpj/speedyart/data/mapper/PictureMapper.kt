package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.queryresult.PictureWithCompletion
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.model.PictureStatistics
import com.mxpj.speedyart.presentation.ImageToPictureClassParser
import javax.inject.Inject

class PictureMapper @Inject constructor(
    private val difficultyLevelMapper: DifficultyLevelMapper
) {

    fun mapPictureWithCompletionListToPictureCompletionList(
        pictureWithCompletionList: List<PictureWithCompletion>
    ): List<PictureCompletion> {
        return pictureWithCompletionList.map { mapPictureWithCompletionToPictureCompletion(it) }
    }

    fun mapPictureWithCompletionToPictureStatistics(
        pictureWithCompletion: PictureWithCompletion
    ): PictureStatistics {
        return PictureStatistics(
            mapPictureWithCompletionToPictureCompletion(pictureWithCompletion),
            getTimePairRepresentation(pictureWithCompletion.picture.bestTime),
        )
    }

    fun mapPictureWithCompletionToPictureCompletion(
        pictureWithCompletion: PictureWithCompletion
    ): PictureCompletion {
        return PictureCompletion(
            pictureWithCompletion.picture.id,
            pictureWithCompletion.picture.assetLink.toInt(),
            difficultyLevelMapper.mapCompletionWithDifficultyListToDifficultyLevelList(
                pictureWithCompletion.completionWithDifficultyList
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