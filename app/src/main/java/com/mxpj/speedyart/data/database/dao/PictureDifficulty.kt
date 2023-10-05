package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.mxpj.speedyart.data.database.model.PictureDifficultyDbModel

@Dao
interface PictureDifficulty {

    @Update
    fun update(pictureDifficultyDbModel: PictureDifficultyDbModel)

    @Query("SELECT * FROM picture_difficulty p WHERE p.id = :id")
    fun getPictureDifficultyById(id: Int)
}