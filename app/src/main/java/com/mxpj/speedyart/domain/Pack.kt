package com.mxpj.speedyart.domain

data class Pack(
    val name: String,
    val size: Pair<Int,Int>,
    val amountOfImages: Int,
    val completionPercent: Float
) {
}