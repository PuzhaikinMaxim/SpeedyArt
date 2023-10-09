package com.mxpj.speedyart.data.database.queryresult

import androidx.room.Embedded
import androidx.room.Relation
import com.mxpj.speedyart.data.database.model.DifficultyDbModel
import com.mxpj.speedyart.data.database.model.PictureCompletionDbModel

data class CompletionWithDifficulty(
    @Embedded
    val pictureCompletionDbModel: PictureCompletionDbModel,
    @Relation(entity = DifficultyDbModel::class, parentColumn = "difficulty", entityColumn = "name")
    val difficultyDbModel: DifficultyDbModel
)