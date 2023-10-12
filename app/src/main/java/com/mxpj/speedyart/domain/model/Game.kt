package com.mxpj.speedyart.domain.model

data class Game(
    val imageId: Int,
    val difficultyLevel: DifficultyLevel,
    val time: Int,
    val amountOfMistakes: Int,
    val result: GameResult
) {
}