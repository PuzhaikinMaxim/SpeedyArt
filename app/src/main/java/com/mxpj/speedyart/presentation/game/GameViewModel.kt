package com.mxpj.speedyart.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.GameResult
import com.mxpj.speedyart.domain.Picture
import kotlinx.coroutines.*

class GameViewModel: ViewModel() {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    private val _healthAmount = MutableLiveData<Int>(4)
    val healthAmount: LiveData<Int>
        get() = _healthAmount

    private val _timer = MutableLiveData(5)
    val timer: LiveData<Int>
        get() = _timer

    private val _gameResult = MutableLiveData(GameResult.GAME_CONTINUING)
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _selectedColor = MutableLiveData<Int>()
    val selectedColor: LiveData<Int>
        get() = _selectedColor

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var coroutine: Job = getTimerCoroutine()

    fun setPicture(newPicture: Picture){
        _picture.value = newPicture
    }

    fun selectColor(colorCode: Int) {
        _selectedColor.value = colorCode
    }

    fun onClick(color: Int?, cellPosition: Pair<Int, Int>) {
        if(gameResult.value == GameResult.GAME_WON || gameResult.value == GameResult.GAME_LOST) {
            return
        }

        val cell = picture.value!!.getCell(cellPosition.first, cellPosition.second)
        if(color != null && color == cell.rightColor){
            cell.currentColor = color
            picture.value!!.unfilledCells.remove(cellPosition)
            _picture.value = _picture.value
            setGameWonIfNoUnfilledCells()
            resetTimer()
        }
        else{
            _healthAmount.value = _healthAmount.value!!.minus(1)
            if(_healthAmount.value!! == 0){
                setGameLost()
            }
        }
    }

    private fun setGameWonIfNoUnfilledCells() {
        if(picture.value!!.unfilledCells.size > 0) return
        _gameResult.value = GameResult.GAME_WON
        coroutine.cancel()
    }

    private fun resetTimer() {
        resetCoroutineScope()
        _timer.value = 5
    }

    private fun resetCoroutineScope() {
        coroutine.cancel()
        coroutine = getTimerCoroutine()
    }

    private fun getTimerCoroutine() = coroutineScope.launch {
        while (_timer.value!! > 0){
            yield()
            delay(1000)
            _timer.postValue(_timer.value!!.minus(1))
        }
        _gameResult.postValue(GameResult.GAME_LOST)
    }

    private fun setGameLost() {
        _gameResult.postValue(GameResult.GAME_LOST)
        coroutine.cancel()
    }

    private companion object {

        private const val MAX_MISTAKES_COUNT = 4

        private const val DELAY_TIME = 10
    }
}