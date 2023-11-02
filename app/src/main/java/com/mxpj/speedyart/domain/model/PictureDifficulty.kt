package com.mxpj.speedyart.domain.model

data class PictureDifficulty(
    val id: Int,
    val pictureAsset: String,
    val difficultyLevel: DifficultyLevel
)