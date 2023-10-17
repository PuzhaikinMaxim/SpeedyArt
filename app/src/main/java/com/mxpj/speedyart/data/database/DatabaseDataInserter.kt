package com.mxpj.speedyart.data.database

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
    private val pictureDao: PictureDao
) {

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            insertDifficulties()
            insertPacks()
            insertPictures()
            insertPictureCompletions()
        }
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
            PackDbModel("Простые изображения",Pair(8,8))
        )
        packDao.insertPackList(packs)
    }

    private suspend fun insertPictures() {
        val pictures = listOf(
            PictureDbModel(DEFAULT_ID,"Простые изображения", R.drawable.heart.toString())
        )
        pictureDao.insertPictureList(pictures)
    }

    private suspend fun insertPictureCompletions() {
        val pictureCompletions = mutableListOf<CompletionDbModel>()
        for(difficulty in getDifficulties()){
            val status = if(difficulty.name == DifficultyDbModel.DIFFICULTY_EASY) CompletionDbModel.STATUS_UNLOCKED else CompletionDbModel.STATUS_LOCKED
            pictureCompletions.add(
                CompletionDbModel(DEFAULT_ID,1, status, null, difficulty.name, null)
            )
        }
        pictureCompletionDao.insertPictureCompletion(pictureCompletions)
    }

    companion object {
        private const val DEFAULT_ID = 0
    }
}