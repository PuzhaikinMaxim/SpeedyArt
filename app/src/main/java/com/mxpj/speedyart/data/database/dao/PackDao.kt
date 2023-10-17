package com.mxpj.speedyart.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mxpj.speedyart.data.database.model.PackDbModel
import com.mxpj.speedyart.data.database.queryresult.PackWithCompletion
import javax.inject.Inject

@Dao
interface PackDao {

    @Query("SELECT p.name, p.size, COUNT(pic.id) AS pictures, COUNT(dif.completionStatus IS 'completed') AS completed, COUNT(dif.completionStatus IS 'perfect') AS perfect FROM pack p JOIN picture pic on p.name = pic.pack JOIN picture_completion dif ON dif.picture = pic.id GROUP BY p.name")
    fun getPackListWithProgress(): LiveData<List<PackWithCompletion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPackList(packList: List<PackDbModel>)
}