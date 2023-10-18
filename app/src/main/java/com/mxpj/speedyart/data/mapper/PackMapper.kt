package com.mxpj.speedyart.data.mapper

import com.mxpj.speedyart.data.database.queryresult.PackWithCompletion
import com.mxpj.speedyart.domain.model.Pack
import javax.inject.Inject

class PackMapper @Inject constructor() {

    fun mapPackWithProgressListToPack(list: List<PackWithCompletion>): List<Pack> {
        return list.map {
            mapPackWithProgressToPack(it)
        }
    }

    fun mapPackWithProgressToPack(packWithCompletion: PackWithCompletion): Pack {
        return Pack(
            packWithCompletion.name,
            packWithCompletion.size,
            packWithCompletion.pictures,
            calculateProgress(packWithCompletion)
        )
    }

    private fun calculateProgress(packWithCompletion: PackWithCompletion): Float {
        with(packWithCompletion) {
            val amountToComplete =
                pictures * DIFFICULTIES_IN_PICTURE * DIFFICULTY_PROGRESS_MULTIPLIER
            return (perfect.toFloat() + completed) / amountToComplete
        }
    }

    companion object {
        private const val DIFFICULTIES_IN_PICTURE = 3
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
    }
}