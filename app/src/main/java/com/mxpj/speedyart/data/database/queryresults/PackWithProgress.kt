package com.mxpj.speedyart.data.database.queryresults

data class PackWithProgress(
    val name: String,
    val size: String,
    val pictures: Int,
    val passed: Int,
    val perfect: Int
)