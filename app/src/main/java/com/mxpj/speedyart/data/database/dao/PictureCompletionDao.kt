package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.mxpj.speedyart.data.database.model.PictureCompletionDbModel
import com.mxpj.speedyart.domain.PictureCompletion

@Dao
interface PictureCompletionDao {

    @Update
    fun update(pictureCompletionDbModel: PictureCompletionDbModel)

    @Query("SELECT * FROM picture_completion p WHERE p.id = :id")
    fun getPictureCompletionById(id: Int): PictureCompletionDbModel
}