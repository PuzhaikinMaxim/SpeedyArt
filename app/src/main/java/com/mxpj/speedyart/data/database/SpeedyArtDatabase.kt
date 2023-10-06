package com.mxpj.speedyart.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mxpj.speedyart.data.database.model.DifficultyDbModel
import com.mxpj.speedyart.data.database.model.PackDbModel
import com.mxpj.speedyart.data.database.model.PictureDbModel
import com.mxpj.speedyart.data.database.model.PictureDifficultyDbModel

@Database(entities = [
    DifficultyDbModel::class,
    PackDbModel::class,
    PictureDbModel::class,
    PictureDifficultyDbModel::class
], version = 1, exportSchema = false)
abstract class SpeedyArtDatabase: RoomDatabase() {

    companion object {

        private var db: SpeedyArtDatabase? = null
        private const val DB_NAME = "speedy_art.db"
        private val LOCK = Any()

        fun getInstance(context: Context): SpeedyArtDatabase {
            synchronized(LOCK){
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    SpeedyArtDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }
}