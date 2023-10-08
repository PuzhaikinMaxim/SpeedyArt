package com.mxpj.speedyart.data.database.queryresults

data class PackWithProgress(
    val name: String,
    val size: Pair<Int, Int>,
    val pictures: Int,
    val passed: Int,
    val perfect: Int
)