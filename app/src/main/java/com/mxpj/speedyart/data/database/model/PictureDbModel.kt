package com.mxpj.speedyart.data.database.model

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
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pack: String,
    val assetLink: String,
    val bestTime: Int? = null
) {
}