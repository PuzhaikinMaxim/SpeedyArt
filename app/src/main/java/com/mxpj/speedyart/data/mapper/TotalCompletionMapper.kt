package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.queryresult.TotalCompletionQueryResult
import com.mxpj.speedyart.domain.model.TotalCompletion
import javax.inject.Inject

class TotalCompletionMapper @Inject constructor() {

    fun mapTotalCompletionQueryResultToTotalCompletion(
        totalCompletionQueryResult: TotalCompletionQueryResult
    ): TotalCompletion {
        return with(totalCompletionQueryResult) {
            TotalCompletion(
                total,
                perfect,
                completed,
                calculatePercents(total, perfect),
                calculatePercents(total, completed),
                calculateTotalCompletionPercent(completed, perfect, total)
            )
        }
    }

    private fun calculateTotalCompletionPercent(completed: Int, perfect: Int, total: Int): Float {
        val maxProgress = total * DIFFICULTY_PROGRESS_MULTIPLIER
        return (perfect.toFloat() + completed) / maxProgress
    }

    private fun calculatePercents(total: Int, stat: Int) = stat.toFloat()/total

    companion object {
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
    }
}