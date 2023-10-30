package com.mxpj.speedyart.domain.model

data class GameColorsData(
    val selectedColor: Int? = null,
    val coloredCellsAmount: Map<Int, Int> = hashMapOf()
)