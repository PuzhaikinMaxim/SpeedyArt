package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mxpj.speedyart.data.database.model.CompletionDbModel
import com.mxpj.speedyart.data.database.queryresult.TotalCompletion

@Dao
interface PictureCompletionDao {

    @Insert
    fun insertPictureCompletion(completionDbModelList: List<CompletionDbModel>)

    @Update
    fun updatePictureCompletionList(completionDbModelList: List<CompletionDbModel>)

    @Update
    fun updatePictureCompletion(completionDbModel: CompletionDbModel)

    @Query("UPDATE picture_completion SET completionStatus = 'locked'")
    fun clearCompletion()

    @Query("SELECT * FROM picture_completion p WHERE p.id = :id")
    fun getPictureCompletionById(id: Int): CompletionDbModel

    @Query("UPDATE picture_completion SET completionStatus = 'unlocked' WHERE difficulty =:unlocking AND picture =:pictureId")
    fun unlockNextDifficulty(pictureId: Int, unlocking: String)

    @Query("SELECT count(*) as total, count(p.completionStatus is 'completed' OR p.completionStatus is 'perfect') as completed, count(p.completionStatus is 'perfect') as perfect FROM picture_completion p")
    fun getTotalCompletion(): TotalCompletion
}