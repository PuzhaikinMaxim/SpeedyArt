package com.mxpj.speedyart.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "picture_completion",
    foreignKeys = [ForeignKey(
        entity = PictureDbModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("picture"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = DifficultyDbModel::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("difficulty"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class CompletionDbModel(
    @PrimaryKey
    val id: Int,
    val picture: Int,
    val completionStatus: String,
    val amountOfMistakes: Int,
    val difficulty: String,
    val time: Int
) {

    companion object {

        const val STATUS_LOCKED = "locked"

        const val STATUS_UNLOCKED = "unlocked"

        const val STATUS_COMPLETED = "completed"

        const val STATUS_PERFECT = "perfect"
    }
}