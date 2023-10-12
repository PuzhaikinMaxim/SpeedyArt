package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mxpj.speedyart.data.database.model.CompletionDbModel

@Dao
interface PictureCompletionDao {

    @Insert
    fun insertPictureCompletion(completionDbModelList: List<CompletionDbModel>)

    @Update
    fun updatePictureCompletionList(completionDbModelList: List<CompletionDbModel>)

    @Update
    fun updatePictureCompletion(completionDbModel: CompletionDbModel)

    @Query("SELECT * FROM picture_completion p WHERE p.id = :id")
    fun getPictureCompletionById(id: Int): CompletionDbModel

    @Query("UPDATE picture_completion SET completionStatus = 'unlocked' WHERE difficulty =:unlocking AND picture =:pictureId")
    fun unlockNextDifficulty(pictureId: Int, unlocking: String)
}