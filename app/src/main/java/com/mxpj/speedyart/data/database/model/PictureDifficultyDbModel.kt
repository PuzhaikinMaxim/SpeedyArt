package com.mxpj.speedyart.data.database.model

data class PictureDifficultyDbModel(
    val id: Int,
    val picture: Int,
    val isLocked: Boolean,
    val isPassed: Boolean,
    val isPerfect: Boolean,
    val amountOfMistakes: Int,
    val difficulty: Boolean,
    val time: Int
) {
}