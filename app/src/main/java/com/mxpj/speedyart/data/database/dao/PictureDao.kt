package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mxpj.speedyart.data.database.model.PictureDbModel
import com.mxpj.speedyart.data.database.queryresult.PictureWithCompletion

@Dao
interface PictureDao {

    @Insert
    suspend fun insertPictureList(pictureDbModelList: List<PictureDbModel>)

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.id JOIN difficulty d ON d.name = p.difficulty WHERE pic.pack = :pack")
    suspend fun getPictureListWithDifficultyProgress(pack: String): List<PictureWithCompletion>

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.id JOIN difficulty d ON d.name = p.difficulty WHERE pic.id = :id")
    suspend fun getPictureWithDifficultyProgress(id: Int): PictureWithCompletion

    @Update
    suspend fun updatePicture(pictureDbModel: PictureDbModel)
}