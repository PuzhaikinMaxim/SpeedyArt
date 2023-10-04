package com.mxpj.speedyart.data.database.model

import androidx.room.PrimaryKey

data class PackDbModel(
    @PrimaryKey
    val name: String,
    val size: Pair<Int,Int>
) {
}