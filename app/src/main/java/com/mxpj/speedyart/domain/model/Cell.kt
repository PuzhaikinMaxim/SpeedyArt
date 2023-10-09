package com.mxpj.speedyart.domain.model

data class Cell(
    val xPosition: Int,
    val yPosition: Int,
    var currentColor: Int,
    val rightColor: Int,
    val isBackground: Boolean = false
)