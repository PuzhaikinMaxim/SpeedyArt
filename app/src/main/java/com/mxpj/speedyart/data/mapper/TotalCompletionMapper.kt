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
                getPercents(total, perfect),
                getPercents(total, completed)
            )
        }
    }

    private fun getPercents(total: Int, stat: Int) = stat.toFloat()/total
}