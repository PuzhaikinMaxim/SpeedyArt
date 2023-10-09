package com.mxpj.speedyart.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mxpj.speedyart.data.database.queryresult.PictureWithCompletion

@Dao
interface PictureDao {

    @Query("SELECT * FROM picture pic JOIN picture_completion p ON p.picture = pic.id JOIN difficulty d ON d.name = p.difficulty WHERE pic.pack = :pack")
    fun getPictureWithDifficultyProgress(pack: String): List<PictureWithCompletion>
}