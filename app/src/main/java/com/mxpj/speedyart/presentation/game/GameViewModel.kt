package com.mxpj.speedyart.presentation.game

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.*
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.GameController
import com.mxpj.speedyart.domain.GameControllerObserver
import com.mxpj.speedyart.domain.model.*
import com.mxpj.speedyart.domain.repository.AppThemeRepository
import com.mxpj.speedyart.domain.repository.DifficultySettingsRepository
import com.mxpj.speedyart.domain.repository.PictureCompletionRepository
import com.mxpj.speedyart.presentation.BitmapToPictureClassParser
import com.mxpj.speedyart.presentation.PixelImageProvider
import com.mxpj.speedyart.presentation.navigation.GameNavParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val gameCountdown: GameCountdown,
    private val pictureCompletionRepository: PictureCompletionRepository,
    private val difficultySettingsRepository: DifficultySettingsRepository,
    val themeRepository: AppThemeRepository,
    private val simpleTimer: SimpleTimer
): ViewModel(), GameControllerObserver {

    private val _gamePicture = MutableLiveData<GamePicture>()
    val gamePicture: LiveData<GamePicture>
        get() = _gamePicture

    private val _pictureBitmap = MutableLiveData<ImageBitmap>()
    val pictureBitmap: LiveData<ImageBitmap>
        get() = _pictureBitmap

    private val _healthAmount = MutableLiveData(4)
    val healthAmount: LiveData<Int>
        get() = _healthAmount

    private val _mistakesAmount = MutableLiveData(0)
    val mistakesAmount: LiveData<Int>
        get() = _mistakesAmount

    private val _shouldResetTimer = MutableLiveData<Unit>()
    val shouldResetTimer: LiveData<Unit>
        get() = _shouldResetTimer

    private val _shouldStopTimer = MutableLiveData(false)
    val shouldStopTimer: LiveData<Boolean>
        get() = _shouldStopTimer

    private val _time = MutableLiveData(0)
    val time: LiveData<Int>
        get() = _time

    private val _shouldShowStartGameModal = MutableLiveData(true)
    val shouldShowStartGameModal: LiveData<Boolean>
        get() = _shouldShowStartGameModal

    private val _shouldShowEndGameModal = MutableLiveData(false)
    val shouldShowEndGameModal: LiveData<Boolean>
        get() = _shouldShowEndGameModal

    private val _gameResult = MutableLiveData(GameResult.GAME_CONTINUING)
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _gameColorsData = MutableLiveData(GameColorsData(null, hashMapOf()))
    val gameColorsData: LiveData<GameColorsData>
        get() = _gameColorsData

    private val _isPictureDataLoaded = MutableLiveData(false)
    val isPictureDataLoaded: LiveData<Boolean>
        get() = _isPictureDataLoaded

    private val _animationTimer = MutableLiveData(8000L)
    val animationTimer: LiveData<Long>
        get() = _animationTimer

    private val gameController = GameController(
        this
    )

    private val theme = themeRepository.getAppTheme()

    private lateinit var pictureDifficulty: PictureDifficulty

    private var isClickLocked = false

    init {
        val completionId = savedStateHandle.get<String>(GameNavParams.completionIdArg)!!.toInt()
        viewModelScope.launch(Dispatchers.IO) {
            pictureDifficulty = pictureCompletionRepository.getPictureDifficulty(completionId)
            println(pictureDifficulty)
            withContext(Dispatchers.Main){
                setupGameViewModel()
            }
        }
    }

    private fun setupGameViewModel() {
        _gamePicture.value = BitmapToPictureClassParser().parseToPicture(
            PixelImageProvider.getPixelBitmap(pictureDifficulty.pictureAsset.toInt())
        )
        val pictureCells = _gamePicture.value!!.gridCells
        val difficultySettings = difficultySettingsRepository.getDifficultySettings(
            pictureDifficulty.difficultyLevel,
            Pair(pictureCells[0].size, pictureCells.size)
        )
        _animationTimer.value = difficultySettings.delay
        gameController.setDifficultySettings(difficultySettings)
        _pictureBitmap.value = getPictureBitmap()
        gameCountdown.startCountdown {
            gameController.startGame(_gamePicture.value!!)
            _shouldShowStartGameModal.postValue(false)
            _shouldResetTimer.postValue(Unit)
            simpleTimer.startTimer()
        }
        _isPictureDataLoaded.value = true
    }

    override fun onPictureChange(gamePicture: GamePicture) {
        _gamePicture.value = gamePicture
        _pictureBitmap.value = getPictureBitmap()
    }

    override fun onHealthAmountChange(healthAmount: Int) {
        _healthAmount.value = healthAmount
        _mistakesAmount.value = 4 - healthAmount
    }

    override fun onGameColorsDataChange(gameColorsData: GameColorsData) {
        _gameColorsData.value = gameColorsData
    }

    override fun onGameResultChange(gameResult: GameResult) {
        val game = Game(
            pictureDifficulty,
            simpleTimer.amountOfSecondsPassed,
            mistakesAmount.value!!,
            gameResult
        )
        viewModelScope.launch {
            pictureCompletionRepository.changePictureCompletion(game)
        }
        _gameResult.postValue(gameResult)
        _shouldShowEndGameModal.postValue(true)
        _time.postValue(simpleTimer.amountOfSecondsPassed)
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

    fun resetGame() {
        _gamePicture.value = BitmapToPictureClassParser().parseToPicture(
            PixelImageProvider.getPixelBitmap(R.drawable.heart_test)
        )
        gameController.resetGame(_gamePicture.value!!)
        _pictureBitmap.value = getPictureBitmap()
        _shouldShowEndGameModal.postValue(false)
        _shouldResetTimer.postValue(Unit)
        simpleTimer.startTimer()
    }

    private fun getPictureBitmap(): ImageBitmap {
        return PictureDrawer(
            _gamePicture.value!!,
            _gameColorsData.value!!.selectedColor,
            600,
            theme
        ).getPictureBitmap().asImageBitmap()
    }

    override fun onCleared() {
        gameCountdown.stopCountdown()
        gameController.stopGame()
        super.onCleared()
    }
}