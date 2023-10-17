package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.mxpj.speedyart.data.database.model.DifficultyDbModel

@Dao
interface DifficultyDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertDifficultyList(difficultyList: List<DifficultyDbModel>)
}