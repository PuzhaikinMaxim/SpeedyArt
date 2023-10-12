package com.mxpj.speedyart.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "difficulty")
data class DifficultyDbModel(
    @PrimaryKey
    val name: String,
    val unlocking: String = ""
) {

    companion object {

        const val DIFFICULTY_EASY = "easy"

        const val DIFFICULTY_MEDIUM = "medium"

        const val DIFFICULTY_HARD = "hard"
    }
}