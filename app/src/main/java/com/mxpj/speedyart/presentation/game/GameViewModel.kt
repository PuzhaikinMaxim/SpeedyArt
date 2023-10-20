package com.mxpj.speedyart.presentation.game

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.model.GameColorsData
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.presentation.ImageToPictureClassParser
import com.mxpj.speedyart.presentation.PixelImageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    private val _pictureBitmap = MutableLiveData<ImageBitmap>()
    val pictureBitmap: LiveData<ImageBitmap>
        get() = _pictureBitmap

    private val _healthAmount = MutableLiveData(4)
    val healthAmount: LiveData<Int>
        get() = _healthAmount

    private val _shouldResetTimer = MutableLiveData(Unit)
    val shouldResetTimer: LiveData<Unit>
        get() = _shouldResetTimer

    private val _gameResult = MutableLiveData(GameResult.GAME_CONTINUING)
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _gameColorsData = MutableLiveData(GameColorsData(null, hashMapOf()))
    val gameColorsData: LiveData<GameColorsData>
        get() = _gameColorsData

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var coroutine: Job = getTimerCoroutine()

    init {
        _picture.value = ImageToPictureClassParser().parseToPicture(
            PixelImageProvider.getPixelBitmap(R.drawable.heart)
        )
        _pictureBitmap.value = getPictureBitmap()
    }

    private fun getPictureBitmap(): ImageBitmap {
        return PictureDrawer(
            _picture.value!!,
            _gameColorsData.value!!.selectedColor,
            600
        ).getPictureBitmap().asImageBitmap()
    }

    fun setPicture(newPicture: Picture){
        _picture.value = newPicture
    }

    fun selectColor(colorCode: Int) {
        _gameColorsData.value = _gameColorsData.value!!.copy(
            selectedColor = colorCode,
            coloredCellsAmount = getColoredCellsAmount()
        )
        _pictureBitmap.value = getPictureBitmap()
    }

    private fun getColoredCellsAmount(): Map<Int, Int> {
        val coloredCellsMap = HashMap<Int, Int>()
        for((color, colorCellAmount) in _picture.value!!.colorsAmount) {
            coloredCellsMap[color] = colorCellAmount
        }
        return coloredCellsMap
    }

    fun onClick(cellPosition: Pair<Int, Int>) {
        if(gameResult.value == GameResult.GAME_WON || gameResult.value == GameResult.GAME_LOST) {
            return
        }
        val color = gameColorsData.value?.selectedColor

        val cell = picture.value!!.getCell(cellPosition.first, cellPosition.second)
        if(color != null && color == cell.rightColor && cell.rightColor != cell.currentColor){
            cell.currentColor = color
            picture.value!!.unfilledCells.remove(cellPosition)
            val amountOfCellsWithColor = max(picture.value!!.colorsAmount[color]!! - 1,0)
            picture.value!!.colorsAmount[color] = amountOfCellsWithColor
            _picture.value = picture.value
            _pictureBitmap.value = getPictureBitmap()
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

        private const val ONE_SECOND = 1000L

        const val EIGHT_SECONDS = 8 * ONE_SECOND
    }
}