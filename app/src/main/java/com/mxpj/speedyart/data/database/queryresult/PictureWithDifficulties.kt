package com.mxpj.speedyart.data.database.queryresult

import androidx.room.Embedded
import androidx.room.Relation
import com.mxpj.speedyart.data.database.model.PictureDbModel
import com.mxpj.speedyart.data.database.model.CompletionDbModel

data class PictureWithDifficulties(
    @Embedded
    val picture: PictureDbModel,
    @Relation(entity = CompletionDbModel::class, parentColumn = "id", entityColumn = "picture")
    val completionWithDifficultyList: List<CompletionWithDifficulty>
) {
}