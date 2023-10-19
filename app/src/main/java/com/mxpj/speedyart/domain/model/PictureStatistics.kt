package com.mxpj.speedyart.domain.model

data class PictureStatistics(
    val pictureCompletion: PictureCompletion,
    val time: Pair<Int, Int>?,
    val amountOfCells: Int = 0,
    val size: Pair<Int, Int> = Pair(0,0)
) {
}