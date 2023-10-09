package com.mxpj.speedyart.data.database.queryresult

data class PackWithCompletion(
    val name: String,
    val size: Pair<Int, Int>,
    val pictures: Int,
    val completed: Int,
    val perfect: Int
)