package com.mxpj.speedyart.domain.model

data class TotalCompletion(
    val total: Int,
    val perfectAmount: Int,
    val completedAmount: Int,
    val perfectPercent: Float,
    val completedPercent: Float
)