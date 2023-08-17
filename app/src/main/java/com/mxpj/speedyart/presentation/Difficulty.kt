package com.mxpj.speedyart.presentation

sealed class Difficulty(val difficultyLevel: DifficultyLevel) {

    class DifficultyLocked(difficultyLevel: DifficultyLevel): Difficulty(difficultyLevel)

    class DifficultyUnlocked(difficultyLevel: DifficultyLevel): Difficulty(difficultyLevel)

    class DifficultyCompleted(difficultyLevel: DifficultyLevel): Difficulty(difficultyLevel)

    class DifficultyPerfect(difficultyLevel: DifficultyLevel): Difficulty(difficultyLevel)
}
