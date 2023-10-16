package com.mxpj.speedyart.data.database.queryresult

data class TotalCompletion(
    val total: Int,
    val completed: Int,
    val perfect: Int
) {
    val perfectRatio = (perfect.toFloat() / completed)

    val completedRatio = (perfect.toFloat() / completed)
}