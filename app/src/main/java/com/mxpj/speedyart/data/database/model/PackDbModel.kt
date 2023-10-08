package com.mxpj.speedyart.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mxpj.speedyart.data.database.converter.PairConverter

@Entity(
    tableName = "pack"
)
data class PackDbModel(
    @PrimaryKey
    val name: String,
    @field:TypeConverters(PairConverter::class)
    val size: Pair<Int, Int>
) {
}