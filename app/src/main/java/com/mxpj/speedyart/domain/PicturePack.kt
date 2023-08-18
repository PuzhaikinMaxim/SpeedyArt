package com.mxpj.speedyart.domain

data class PicturePack(
    val name: String,
    val size: Pair<Int,Int>,
    val amountOfImages: Int,
    val completionPercent: Float
) {
}