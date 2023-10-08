package com.mxpj.speedyart.data.mappers

import com.mxpj.speedyart.data.database.queryresults.PackWithProgress
import com.mxpj.speedyart.domain.Pack
import javax.inject.Inject

class PackMapper @Inject constructor() {

    fun mapPackWithProgressListToPack(list: List<PackWithProgress>): List<Pack> {
        return list.map { mapPackWithProgressToPack(it) }
    }

    fun mapPackWithProgressToPack(packWithProgress: PackWithProgress): Pack {
        return Pack(
            packWithProgress.name,
            packWithProgress.size,
            packWithProgress.pictures,
            calculateProgress(packWithProgress)
        )
    }

    private fun calculateProgress(packWithProgress: PackWithProgress): Float {
        with(packWithProgress) {
            val amountToComplete =
                pictures * DIFFICULTIES_IN_PICTURE * DIFFICULTY_PROGRESS_MULTIPLIER
            return (perfect.toFloat() * PERFECT_WEIGHT + passed) / amountToComplete
        }
    }

    companion object {
        private const val DIFFICULTIES_IN_PICTURE = 3
        private const val DIFFICULTY_PROGRESS_MULTIPLIER = 2
        private const val PERFECT_WEIGHT = 2
    }
}