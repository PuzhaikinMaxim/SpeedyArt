package com.mxpj.speedyart.data.database.dao

import androidx.room.*
import com.mxpj.speedyart.data.database.model.PictureDbModel
import com.mxpj.speedyart.data.database.queryresult.PictureWithCompletion

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureList(pictureDbModelList: List<PictureDbModel>)

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.id JOIN difficulty d ON d.name = p.difficulty WHERE pic.pack = :pack GROUP BY picture")
    suspend fun getPictureListWithDifficultyProgress(pack: String): List<PictureWithCompletion>

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.id JOIN difficulty d ON d.name = p.difficulty WHERE pic.id = :id GROUP BY picture")
    suspend fun getPictureWithDifficultyProgress(id: Int): PictureWithCompletion

    @Update
    suspend fun updatePicture(pictureDbModel: PictureDbModel)
}