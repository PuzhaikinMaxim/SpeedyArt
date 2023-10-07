package com.mxpj.speedyart.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "picture",
    foreignKeys = [ForeignKey(
        entity = PackDbModel::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("pack"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class PictureDbModel(
    @PrimaryKey
    val id: Int,
    val resource: Int,
    val pack: Int,
    val assetLink: String,
    val bestTime: Int
) {
}