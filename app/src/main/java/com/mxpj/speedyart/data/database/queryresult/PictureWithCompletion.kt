package com.mxpj.speedyart.data.database.queryresult

import androidx.room.Embedded
import androidx.room.Relation
import com.mxpj.speedyart.data.database.model.PictureDbModel
import com.mxpj.speedyart.data.database.model.PictureCompletionDbModel

data class PictureWithCompletion(
    @Embedded
    val picture: PictureDbModel,
    @Relation(entity = PictureCompletionDbModel::class, parentColumn = "id", entityColumn = "picture")
    val completionWithDifficultyList: List<CompletionWithDifficulty>
) {
}