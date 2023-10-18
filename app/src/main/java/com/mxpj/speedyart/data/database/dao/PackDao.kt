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

    @Query("SELECT p.name, " +
            "p.size, " +
            "COUNT(pic.id) AS pictures, " +
            "sum(pic.completed) AS completed, " +
            "sum(pic.perfect) AS perfect " +
            "FROM pack p " +
            "JOIN (" +
            "SELECT picture.id, picture.pack, " +
            "sum(CASE WHEN dif.completionStatus IS 'completed' THEN 1 ELSE 0 END) AS completed, " +
            "sum(CASE WHEN dif.completionStatus IS 'perfect' THEN 1 ELSE 0 END) AS perfect " +
            "FROM picture " +
            "JOIN picture_completion dif ON dif.picture = picture.id " +
            "GROUP BY picture.id" +
            ") as pic on p.name = pic.pack " +
            "GROUP BY p.name")
    fun getPackListWithProgress(): LiveData<List<PackWithCompletion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPackList(packList: List<PackDbModel>)
}