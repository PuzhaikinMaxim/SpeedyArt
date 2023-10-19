package com.mxpj.speedyart.data.database.dao

import androidx.room.*
import com.mxpj.speedyart.data.database.model.CompletionDbModel
import com.mxpj.speedyart.data.database.queryresult.TotalCompletionQueryResult

@Dao
interface PictureCompletionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureCompletion(completionDbModelList: List<CompletionDbModel>)

    @Update
    suspend fun updatePictureCompletionList(completionDbModelList: List<CompletionDbModel>)

    @Update
    suspend fun updatePictureCompletion(completionDbModel: CompletionDbModel)

    @Query("UPDATE picture_completion SET completionStatus = CASE WHEN difficulty != 'easy' THEN 'locked' ELSE 'unlocked' END")
    fun clearCompletion()

    @Query("SELECT * FROM picture_completion p WHERE p.id = :id")
    fun getPictureCompletionById(id: Int): CompletionDbModel

    @Query("UPDATE picture_completion SET completionStatus = 'unlocked' WHERE difficulty =:unlocking AND picture =:pictureId")
    fun unlockNextDifficulty(pictureId: Int, unlocking: String)

    @Query("SELECT count(*) as total, " +
            "sum(CASE WHEN p.completionStatus is 'completed' OR p.completionStatus is 'perfect' THEN 1 ELSE 0 END) as completed, " +
            "sum(CASE WHEN p.completionStatus is 'perfect' THEN 1 ELSE 0 END) as perfect " +
            "FROM picture_completion p")
    fun getTotalCompletion(): TotalCompletionQueryResult
}