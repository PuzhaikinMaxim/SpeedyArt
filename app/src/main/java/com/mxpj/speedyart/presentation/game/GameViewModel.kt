package com.mxpj.speedyart.presentation.game

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.GameController
import com.mxpj.speedyart.domain.GameControllerObserver
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
    savedStateHandle: SavedStateHandle,
    val gameCountdown: GameCountdown
): ViewModel(), GameControllerObserver {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    private val _pictureBitmap = MutableLiveData<ImageBitmap>()
    val pictureBitmap: LiveData<ImageBitmap>
        get() = _pictureBitmap

    private val _healthAmount = MutableLiveData(4)
    val healthAmount: LiveData<Int>
        get() = _healthAmount

    private val _shouldResetTimer = MutableLiveData<Unit>()
    val shouldResetTimer: LiveData<Unit>
        get() = _shouldResetTimer

    private val _shouldStopTimer = MutableLiveData(false)
    val shouldStopTimer: LiveData<Boolean>
        get() = _shouldStopTimer

    private val _shouldShowStartGameModal = MutableLiveData(true)
    val shouldShowStartGameModal: LiveData<Boolean>
        get() = _shouldShowStartGameModal

    private val _gameResult = MutableLiveData(GameResult.GAME_CONTINUING)
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _gameColorsData = MutableLiveData(GameColorsData(null, hashMapOf()))
    val gameColorsData: LiveData<GameColorsData>
        get() = _gameColorsData

    private val gameController = GameController(this)

    init {
        _picture.value = ImageToPictureClassParser().parseToPicture(
            PixelImageProvider.getPixelBitmap(R.drawable.heart)
        )
        _pictureBitmap.value = getPictureBitmap()
        gameCountdown.startCountdown {
            gameController.startGame(_picture.value!!)
            _shouldShowStartGameModal.postValue(false)
            _shouldResetTimer.postValue(Unit)
        }
    }

    override fun onPictureChange(picture: Picture) {
        _picture.value = picture
        _pictureBitmap.value = getPictureBitmap()
    }

    override fun onHealthAmountChange(healthAmount: Int) {
        _healthAmount.value = healthAmount
    }

    override fun onGameColorsDataChange(gameColorsData: GameColorsData) {
        _gameColorsData.value = gameColorsData
    }

    override fun onGameResultChange(gameResult: GameResult) {
        _gameResult.postValue(gameResult)
    }

    override fun onTimerReset() {
        _shouldResetTimer.postValue(Unit)
    }

    override fun onTimerStop() {
        _shouldStopTimer.postValue(true)
    }

    fun onClick(cellPosition: Pair<Int, Int>) {
        gameController.paintCell(cellPosition)
    }

    fun selectColor(colorCode: Int) {
        gameController.selectColor(colorCode)
        _pictureBitmap.value = getPictureBitmap()
    }

    private fun getPictureBitmap(): ImageBitmap {
        return PictureDrawer(
            _picture.value!!,
            _gameColorsData.value!!.selectedColor,
            600
        ).getPictureBitmap().asImageBitmap()
    }
}