package com.mxpj.speedyart.domain.model

data class Game(
    val pictureDifficulty: PictureDifficulty,
    val time: Int,
    val amountOfMistakes: Int,
    val result: GameResult
)