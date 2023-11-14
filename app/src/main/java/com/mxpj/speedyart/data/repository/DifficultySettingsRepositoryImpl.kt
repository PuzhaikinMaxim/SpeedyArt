package com.mxpj.speedyart.data.repository

import com.mxpj.speedyart.domain.model.*
import com.mxpj.speedyart.domain.offsetFor16x16
import com.mxpj.speedyart.domain.offsetFor32x32
import com.mxpj.speedyart.domain.offsetFor8x8
import com.mxpj.speedyart.domain.repository.DifficultySettingsRepository
import javax.inject.Inject
import kotlin.math.absoluteValue

class DifficultySettingsRepositoryImpl @Inject constructor(): DifficultySettingsRepository {

    override fun getDifficultySettings(
        difficultyLevel: DifficultyLevel,
        size: Pair<Int, Int>
    ): DifficultySettings {
        val modifier = calculateSizeModifier(size)
        return when(difficultyLevel){
            is LevelEasy -> {
                DifficultySettings(
                    20000L,
                    4,
                    getOffset(size)
                ).getSettingsWithModifier(modifier)
            }
            is LevelMedium -> {
                DifficultySettings(
                    3000L,
                    4,
                    getOffset(size)
                ).getSettingsWithModifier(modifier)
            }
            is LevelHard -> {
                DifficultySettings(
                    2000L,
                    3,
                    getOffset(size)
                ).getSettingsWithModifier(modifier)
            }
        }
    }

    private fun DifficultySettings.getSettingsWithModifier(
        modifier: Float
    ): DifficultySettings {
        return copy(
            delay = (delay * modifier).toLong(),
            maxHealth = (maxHealth * modifier).toInt()
        )
    }

    private fun calculateSizeModifier(size: Pair<Int, Int>): Float {
        val area = size.first * size.second
        val divider = (MAXIMUM_AREA - MINIMUM_AREA) * MODIFIER
        val divided = ((MINIMUM_AREA - area).coerceAtLeast(0))
        return (divided.toFloat()/divider) + 1
    }

    private fun getOffset(size: Pair<Int, Int>): List<Pair<Int, Int>> {
        val area = size.first * size.second
        var minDiffArea = Int.MAX_VALUE
        var offset = offsetFor8x8
        areasList.forEach {
            if((it.first - area).absoluteValue < minDiffArea){
                offset = it.second
                minDiffArea = it.first
            }
        }
        return offset
    }

    companion object {
        private const val MAXIMUM_AREA = 1024
        private const val MINIMUM_AREA = 64
        private const val MODIFIER = 64

        private val areasList = listOf(
            Pair(64, offsetFor8x8),
            Pair(256, offsetFor16x16),
            Pair(1024, offsetFor32x32)
        )
    }
}