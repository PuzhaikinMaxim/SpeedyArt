package com.mxpj.speedyart.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mxpj.speedyart.domain.model.GameColorsData
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.Picture
import javax.inject.Inject
import kotlin.math.max
import kotlin.properties.Delegates

class GameController(
    private val gameControllerObserver: GameControllerObserver
) {

    private var picture: Picture? by Delegates.observable(null, onChange = {
            _, _, newValue -> gameControllerObserver.onPictureChange(newValue!!)
    })

    private var healthAmount: Int by Delegates.observable(4, onChange = {
            _, _, newValue -> gameControllerObserver.onHealthAmountChange(newValue)
    })

    private var gameResult: GameResult by Delegates.observable(GameResult.GAME_CONTINUING, onChange = {
        _, _, newValue -> gameControllerObserver.onGameResultChange(newValue)
    })

    private var gameColorsData: GameColorsData by Delegates.observable(GameColorsData(
        null, hashMapOf()
    ), onChange = {
            _, _, newValue -> gameControllerObserver.onGameColorsDataChange(newValue)
    })

    private val gameTimer = GameTimer(DEFAULT_DELAY_TIME, gameControllerObserver) {
        gameResult = GameResult.GAME_LOST
    }

    fun startGame(newPicture: Picture) {
        picture = newPicture
        gameTimer.start()
    }

    fun paintCell(cellPosition: Pair<Int, Int>) {
        val color = gameColorsData.selectedColor
        val cell = picture!!.getCell(cellPosition.first, cellPosition.second)
        if(color != null && color == cell.rightColor && cell.rightColor != cell.currentColor){
            cell.currentColor = color
            picture!!.unfilledCells.remove(cellPosition)
            val amountOfCellsWithColor = max(picture!!.colorsAmount[color]!! - 1,0)
            picture!!.colorsAmount[color] = amountOfCellsWithColor
            picture = picture
            gameTimer.reset()
            setGameWonIfNoUnfilledCells()
        }
        else{
            healthAmount = healthAmount.minus(1)
            if(healthAmount == 0){
                gameResult = GameResult.GAME_LOST
            }
        }
    }

    fun selectColor(colorCode: Int) {
        gameColorsData = gameColorsData.copy(
            selectedColor = colorCode,
            coloredCellsAmount = getColoredCellsAmount()
        )
    }

    private fun getColoredCellsAmount(): Map<Int, Int> {
        val coloredCellsMap = HashMap<Int, Int>()
        for((color, colorCellAmount) in picture!!.colorsAmount) {
            coloredCellsMap[color] = colorCellAmount
        }
        return coloredCellsMap
    }

    private fun setGameWonIfNoUnfilledCells() {
        if(picture!!.unfilledCells.size > 0){
            return
        }
        gameTimer.stop()
        gameResult = GameResult.GAME_WON
    }

    companion object {
        private const val DEFAULT_DELAY_TIME = 8000L
    }
}