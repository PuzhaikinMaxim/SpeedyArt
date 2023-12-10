package com.mxpj.speedyart.data.database

import android.app.Application
import com.mxpj.speedyart.R
import com.mxpj.speedyart.data.database.dao.DifficultyDao
import com.mxpj.speedyart.data.database.dao.PackDao
import com.mxpj.speedyart.data.database.dao.PictureCompletionDao
import com.mxpj.speedyart.data.database.dao.PictureDao
import com.mxpj.speedyart.data.database.model.CompletionDbModel
import com.mxpj.speedyart.data.database.model.DifficultyDbModel
import com.mxpj.speedyart.data.database.model.PackDbModel
import com.mxpj.speedyart.data.database.model.PictureDbModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DatabaseDataInserter @Inject constructor(
    private val difficultyDao: DifficultyDao,
    private val packDao: PackDao,
    private val pictureCompletionDao: PictureCompletionDao,
    private val pictureDao: PictureDao,
    private val application: Application
) {

    init {
        /*
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            insertDifficulties()
            insertPacks()
            insertPictures()
            insertPictureCompletions()
        }
         */
    }

    private suspend fun insertDifficulties() {
        difficultyDao.insertDifficultyList(getDifficulties())
    }

    private fun getDifficulties() = listOf(
        DifficultyDbModel(DifficultyDbModel.DIFFICULTY_EASY,DifficultyDbModel.DIFFICULTY_MEDIUM),
        DifficultyDbModel(DifficultyDbModel.DIFFICULTY_MEDIUM,DifficultyDbModel.DIFFICULTY_HARD),
        DifficultyDbModel(DifficultyDbModel.DIFFICULTY_HARD)
    )

    private suspend fun insertPacks() {
        val packs = listOf(
            //PackDbModel("Простые изображения",Pair(8,8)),
            PackDbModel(packs[0], Pair(16, 16)),
            PackDbModel(packs[1], Pair(16, 16)),
            PackDbModel(packs[2], Pair(16, 16)),
            PackDbModel(packs[3], Pair(32, 32)),
            PackDbModel(packs[4], Pair(16, 16)),
            PackDbModel(packs[5], Pair(16, 16)),
        )
        packDao.insertPackList(packs)
    }

    private suspend fun insertPictures() {
        val pictures = mutableListOf<PictureDbModel>(
            //PictureDbModel(DEFAULT_ID,"Простые изображения", R.drawable.heart_test.toString())
        )
        val assetManager = application.assets
        for((index, imageFolder) in locations.withIndex()){
            assetManager.list("$DEFAULT_ASSETS_LOCATION/$imageFolder")?.forEach {
                val picture = PictureDbModel(DEFAULT_ID, packs[index], "$DEFAULT_ASSETS_LOCATION/$imageFolder/$it")
                pictures.add(picture)
            }
        }
        pictureDao.insertPictureList(pictures)
    }

    private suspend fun insertPictureCompletions() {
        val pictures = pictureDao.getPictureList()
        val pictureCompletions = mutableListOf<CompletionDbModel>()
        for(picture in pictures) {
            for(difficulty in getDifficulties()){
                val status = if(difficulty.name == DifficultyDbModel.DIFFICULTY_EASY) CompletionDbModel.STATUS_UNLOCKED else CompletionDbModel.STATUS_LOCKED
                pictureCompletions.add(
                    CompletionDbModel(DEFAULT_ID,picture.id, status, null, difficulty.name, null)
                )
            }
        }
        pictureCompletionDao.insertPictureCompletion(pictureCompletions)
    }

    companion object {
        private const val DEFAULT_ID = 0

        private const val DEFAULT_ASSETS_LOCATION = "images"

        private val packs = listOf(
            "Dungeon", "Fantasy", "Fishing", "Food", "Fruit", "Gun"
        )

        private val locations = listOf(
            "dungeon", "fantasy", "fishing", "food", "fruit", "gun"
        )
    }
}