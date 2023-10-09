package com.mxpj.speedyart.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.Picture
import kotlinx.coroutines.*
import kotlin.math.max

class GameViewModel: ViewModel() {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    private val _healthAmount = MutableLiveData<Int>(4)
    val healthAmount: LiveData<Int>
        get() = _healthAmount

    private val _shouldResetTimer = MutableLiveData(Unit)
    val shouldResetTimer: LiveData<Unit>
        get() = _shouldResetTimer

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
        if(color != null && color == cell.rightColor && cell.rightColor != cell.currentColor){
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
        _shouldResetTimer.value = Unit
    }

    private fun resetCoroutineScope() {
        coroutine.cancel()
        coroutine = getTimerCoroutine()
    }

    private fun getTimerCoroutine() = coroutineScope.launch {
        delay(EIGHT_SECONDS)
        _gameResult.postValue(GameResult.GAME_LOST)
    }

    private fun setGameLost() {
        _gameResult.postValue(GameResult.GAME_LOST)
        coroutine.cancel()
    }

    companion object {

        private const val MAX_MISTAKES_COUNT = 4

        //private const val DELAY_TIME = 100L

        private const val ONE_SECOND = 1000L

        const val EIGHT_SECONDS = 8 * ONE_SECOND
    }
}