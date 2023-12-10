package com.mxpj.speedyart.data.database.dao

import androidx.room.*
import com.mxpj.speedyart.data.database.model.PictureDbModel
import com.mxpj.speedyart.data.database.queryresult.PictureWithDifficulties

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureList(pictureDbModelList: List<PictureDbModel>)

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.pictureId JOIN difficulty d ON d.name = p.difficulty WHERE pic.pack = :pack GROUP BY picture")
    suspend fun getPictureListWithDifficultyProgress(pack: String): List<PictureWithDifficulties>

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.pictureId JOIN difficulty d ON d.name = p.difficulty WHERE pic.pictureId = :id GROUP BY picture")
    suspend fun getPictureWithDifficultyProgress(id: Int): PictureWithDifficulties

    @Query("SELECT * FROM picture pic WHERE pic.pictureId = :pictureId")
    suspend fun getPictureById(pictureId: Int): PictureDbModel

    @Query("SELECT * FROM picture")
    suspend fun getPictureList(): List<PictureDbModel>

    @Update
    suspend fun updatePicture(pictureDbModel: PictureDbModel)
}