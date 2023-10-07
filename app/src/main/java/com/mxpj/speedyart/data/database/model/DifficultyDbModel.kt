package com.mxpj.speedyart.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "difficulty")
data class DifficultyDbModel(
    @PrimaryKey
    val name: String,
    val unlocking: String
) {
}