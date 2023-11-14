package com.mxpj.speedyart.domain.model

data class DifficultySettings(
    val delay: Long,
    val maxHealth: Int,
    val offset: List<Pair<Int, Int>>
)