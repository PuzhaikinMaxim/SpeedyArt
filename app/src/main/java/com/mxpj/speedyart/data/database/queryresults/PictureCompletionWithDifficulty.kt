package com.mxpj.speedyart.data.database.queryresults

import androidx.room.Embedded
import androidx.room.Relation
import com.mxpj.speedyart.data.database.model.DifficultyDbModel
import com.mxpj.speedyart.data.database.model.PictureCompletionDbModel

data class PictureCompletionWithDifficulty(
    @Embedded
    val pictureCompletionDbModel: PictureCompletionDbModel,
    @Relation(entity = DifficultyDbModel::class, parentColumn = "difficulty", entityColumn = "name")
    val difficultyDbModel: DifficultyDbModel
)