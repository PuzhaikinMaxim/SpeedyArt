package com.mxpj.speedyart.data.database.converter

import androidx.room.TypeConverter

class PairConverter {

    @TypeConverter
    fun fromPair(pair: Pair<Int, Int>): String {
        return "${pair.first} ${pair.second}"
    }

    @TypeConverter
    fun toPair(string: String): Pair<Int, Int> {
        val numbers = string.split(" ").map { it.toInt() }
        return Pair(numbers[0], numbers[1])
    }
}