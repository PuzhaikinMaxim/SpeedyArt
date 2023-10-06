package com.mxpj.speedyart.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pack"
)
data class PackDbModel(
    @PrimaryKey
    val name: String,
    val size: String
) {
}