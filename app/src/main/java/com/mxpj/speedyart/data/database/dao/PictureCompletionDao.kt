package com.mxpj.speedyart.data.database.dao

import androidx.room.*
import com.mxpj.speedyart.data.database.model.CompletionDbModel
import com.mxpj.speedyart.data.database.queryresult.CompletionWithDifficulty
import com.mxpj.speedyart.data.database.queryresult.CompletionWithPicture
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
    suspend fun clearCompletion()

    @Query("SELECT * FROM picture_completion p WHERE p.completionId = :id")
    suspend fun getPictureCompletionById(id: Int): CompletionDbModel

    @Query("SELECT * FROM picture_completion p JOIN difficulty d ON p.difficulty = d.name WHERE p.completionId = :id")
    suspend fun getPictureCompletionWithDifficulty(id: Int): CompletionWithDifficulty

    @Query("UPDATE picture_completion SET completionStatus = 'unlocked' WHERE difficulty =:unlocking AND picture =:pictureId AND completionStatus = 'locked'")
    suspend fun unlockNextDifficulty(pictureId: Int, unlocking: String)

    @Query("SELECT count(*) as total, " +
            "sum(CASE WHEN p.completionStatus is 'completed' OR p.completionStatus is 'perfect' THEN 1 ELSE 0 END) as completed, " +
            "sum(CASE WHEN p.completionStatus is 'perfect' THEN 1 ELSE 0 END) as perfect " +
            "FROM picture_completion p")
    suspend fun getTotalCompletion(): TotalCompletionQueryResult

    @Query("SELECT * " +
            "FROM picture_completion p " +
            "JOIN picture pic ON pic.pictureId = p.picture " +
            "WHERE p.completionId = :completionId")
    suspend fun getCompletionWithPicture(completionId: Int): CompletionWithPicture
}