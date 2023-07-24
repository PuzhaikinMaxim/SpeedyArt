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

    private val _mistakesAmount = MutableLiveData<Int>(0)
    val mistakesAmount: LiveData<Int>
        get() = _mistakesAmount

    private val _timer = MutableLiveData(5)
    val timer: LiveData<Int>
        get() = _timer

    private val _gameResult = MutableLiveData(GameResult.GAME_CONTINUING)
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var coroutine: Job = coroutineScope.launch {
        while (_timer.value!! > 0){
            delay(1000)
            _timer.postValue(_timer.value!!.minus(1))
        }
        _gameResult.postValue(GameResult.GAME_LOST)
    }

    fun setPicture(newPicture: Picture){
        _picture.value = newPicture
    }

    fun getColor(): Int {
        return picture.value!!.availablePalette[0]
    }

    fun onClick(color: Int, cellPosition: Pair<Int, Int>) {
        val cell = picture.value!!.getCell(cellPosition.first, cellPosition.second)
        if(color == cell.rightColor){
            cell.currentColor = color
            picture.value!!.unfilledCells.remove(cellPosition)
            _picture.value = _picture.value
            if(picture.value!!.unfilledCells.size == 0){
                _gameResult.value = GameResult.GAME_WON
            }
            resetTimer()
        }
        else{
            _mistakesAmount.value = _mistakesAmount.value!!.plus(1)
            if(_mistakesAmount.value!! > MAX_MISTAKES_COUNT){
                _gameResult.value = GameResult.GAME_LOST
            }
        }
    }

    private fun resetTimer() {
        resetCoroutineScope()
        _timer.value = 5
    }

    private fun resetCoroutineScope() {
        coroutine.cancel()
        coroutine = coroutineScope.launch {
            while (_timer.value!! > 0){
                delay(1000)
                _timer.postValue(_timer.value!!.minus(1))
            }
            _gameResult.postValue(GameResult.GAME_LOST)
        }
    }

    private companion object {
        const val MAX_MISTAKES_COUNT = 4
    }
}