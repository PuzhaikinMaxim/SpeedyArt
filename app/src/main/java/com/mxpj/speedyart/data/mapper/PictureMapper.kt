package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.queryresult.PictureWithCompletion
import com.mxpj.speedyart.domain.model.PictureCompletion
import javax.inject.Inject

class PictureMapper @Inject constructor(
    private val difficultyLevelMapper: DifficultyLevelMapper
) {

    fun mapPictureWithCompletionListToPictureCompletionList(
        pictureWithCompletionList: List<PictureWithCompletion>
    ): List<PictureCompletion> {
        return pictureWithCompletionList.map { mapPictureWithCompletionToPictureCompletion(it) }
    }

    fun mapPictureWithCompletionToPictureCompletion(
        pictureWithCompletion: PictureWithCompletion
    ): PictureCompletion {
        return PictureCompletion(
            pictureWithCompletion.picture.resource,
            difficultyLevelMapper.mapCompletionWithDifficultyListToDifficultyLevelList(
                pictureWithCompletion.completionWithDifficultyList
            )
        )
    }
}