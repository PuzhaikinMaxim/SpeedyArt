package com.mxpj.speedyart.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.GameResult
import com.mxpj.speedyart.domain.Picture
import kotlinx.coroutines.*
import kotlin.math.max

class GameViewModel: ViewModel() {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    private val _healthAmount = MutableLiveData<Int>(4)
    val healthAmount: LiveData<Int>
        get() = _healthAmount

    private val _timerProgress = MutableLiveData(0f)
    val timerProgress: LiveData<Float>
        get() = _timerProgress

    private val _gameResult = MutableLiveData(GameResult.GAME_CONTINUING)
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _selectedColor = MutableLiveData<Int>()
    val selectedColor: LiveData<Int>
        get() = _selectedColor

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var coroutine: Job = getTimerCoroutine()

    fun setPicture(newPicture: Picture){
        _picture.value = newPicture
    }

    fun selectColor(colorCode: Int) {
        _selectedColor.value = colorCode
        _picture.value = picture.value
    }

    fun onClick(color: Int?, cellPosition: Pair<Int, Int>) {
        if(gameResult.value == GameResult.GAME_WON || gameResult.value == GameResult.GAME_LOST) {
            return
        }

        val cell = picture.value!!.getCell(cellPosition.first, cellPosition.second)
        if(color != null && color == cell.rightColor){
            cell.currentColor = color
            picture.value!!.unfilledCells.remove(cellPosition)
            val amountOfCellsWithColor = max(picture.value!!.colorsAmount[color]!! - 1,0)
            picture.value!!.colorsAmount[color] = amountOfCellsWithColor
            _picture.value = picture.value
            resetTimer()
            setGameWonIfNoUnfilledCells()
        }
        else{
            _healthAmount.value = _healthAmount.value!!.minus(1)
            if(_healthAmount.value!! == 0){
                setGameLost()
            }
        }
    }

    private fun setGameWonIfNoUnfilledCells() {
        if(picture.value!!.unfilledCells.size > 0){
            return
        }
        coroutine.cancel()
        _gameResult.value = GameResult.GAME_WON
    }

    private fun resetTimer() {
        resetCoroutineScope()
        _timerProgress.value = 0f
    }

    private fun resetCoroutineScope() {
        coroutine.cancel()
        coroutine = getTimerCoroutine()
    }

    private fun getTimerCoroutine() = coroutineScope.launch {
        var progress = 0
        while (progress < EIGHT_SECONDS){
            yield()
            delay(DELAY_TIME)
            progress++
            //println("progress $progress")
            _timerProgress.postValue(calculateProgress(progress))
        }
        _gameResult.postValue(GameResult.GAME_LOST)
    }

    private fun calculateProgress(progress: Int): Float {
        return ((progress * 100 / EIGHT_SECONDS).toFloat() / 100)
    }

    private fun setGameLost() {
        _gameResult.postValue(GameResult.GAME_LOST)
        coroutine.cancel()
    }

    private companion object {

        private const val MAX_MISTAKES_COUNT = 4

        private const val DELAY_TIME = 100L

        private const val ONE_SECOND = 1000L

        private const val EIGHT_SECONDS = 8 * ONE_SECOND / DELAY_TIME
    }
}