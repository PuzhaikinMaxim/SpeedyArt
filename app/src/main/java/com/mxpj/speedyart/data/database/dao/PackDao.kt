package com.mxpj.speedyart.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mxpj.speedyart.data.database.queryresults.PackWithProgress

@Dao
interface PackDao {

    @Query("SELECT p.name, p.size, COUNT(pic.id) AS pictures, COUNT(dif.isPassed IS 1 AND dif.isPerfect IS 0) AS passed, COUNT(dif.isPerfect IS 1) AS perfect FROM pack p JOIN picture pic on p.name = pic.pack JOIN picture_completion dif ON dif.picture = pic.id GROUP BY p.name")
    fun getPackListWithProgress(): LiveData<List<PackWithProgress>>
}