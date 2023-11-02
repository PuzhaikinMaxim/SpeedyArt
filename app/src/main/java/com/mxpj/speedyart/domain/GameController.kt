package com.mxpj.speedyart.domain

import com.mxpj.speedyart.domain.model.Cell
import com.mxpj.speedyart.domain.model.GameColorsData
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.GamePicture
import kotlin.math.max
import kotlin.properties.Delegates

class GameController(
    private val gameControllerObserver: GameControllerObserver
) {

    private var gamePicture: GamePicture? by Delegates.observable(null, onChange = {
            _, _, newValue -> gameControllerObserver.onPictureChange(newValue!!)
    })

    private var healthAmount: Int by Delegates.observable(4, onChange = {
            _, _, newValue -> gameControllerObserver.onHealthAmountChange(newValue)
    })

    private var gameResult: GameResult by Delegates.observable(GameResult.GAME_CONTINUING, onChange = {
        _, _, newValue -> gameControllerObserver.onGameResultChange(newValue)
        gameControllerObserver.onTimerStop()
    })

    private var gameColorsData: GameColorsData by Delegates.observable(GameColorsData(), onChange = {
            _, _, newValue -> gameControllerObserver.onGameColorsDataChange(newValue)
    })

    private val gameTimer = GameTimer(DEFAULT_DELAY_TIME, gameControllerObserver) {
        gameResult = GameResult.GAME_LOST
    }

    private lateinit var neighborCellOffset: List<Pair<Int, Int>>

    fun startGame(newGamePicture: GamePicture) {
        gamePicture = newGamePicture
        neighborCellOffset = offsetFor16x16
        gameTimer.start()
    }

    fun resetGame(newGamePicture: GamePicture) {
        gamePicture = newGamePicture
        neighborCellOffset = offsetFor16x16
        gameResult = GameResult.GAME_CONTINUING
        gameColorsData = GameColorsData()
        healthAmount = 4
        gameTimer.start()
    }

    fun stopGame() {
        gameTimer.stop()
    }

    fun paintCell(cellPosition: Pair<Int, Int>) {
        if(gameResult != GameResult.GAME_CONTINUING) return
        val color = gameColorsData.selectedColor
        val cell = gamePicture!!.getCell(cellPosition.first, cellPosition.second)
        if(color != null && color == cell.rightColor && cell.rightColor != cell.currentColor){
            cell.currentColor = color
            gamePicture!!.unfilledCells.remove(cellPosition)
            val neighborCellsWithSelectedColor = getAvailableForRecolorNeighborCells(cellPosition)
            setRightColorForNeighborCells(neighborCellsWithSelectedColor)
            for(neighborCell in neighborCellsWithSelectedColor){
                gamePicture!!.unfilledCells.remove(Pair(neighborCell.xPosition, neighborCell.yPosition))
            }
            val amountOfCellsWithColor = max(
                gamePicture!!.colorsAmount[color]!! - 1 - neighborCellsWithSelectedColor.size,
                0
            )
            gamePicture!!.colorsAmount[color] = amountOfCellsWithColor
            gamePicture = gamePicture
            gameColorsData = gameColorsData.copy(
                coloredCellsAmount = getColoredCellsAmount()
            )
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
        for((color, colorCellAmount) in gamePicture!!.colorsAmount) {
            coloredCellsMap[color] = colorCellAmount
        }
        return coloredCellsMap
    }

    private fun getAvailableForRecolorNeighborCells(cell: Pair<Int, Int>): List<Cell> {
        val existingCells = neighborCellOffset.mapNotNull {
            val x = cell.first + it.first
            val y = cell.second + it.second
            gamePicture!!.gridCells.getOrNull(y)?.getOrNull(x)
        }
        val cellsWithSelectedColor = existingCells.filter {
            val selectedColor = gameColorsData.selectedColor
            selectedColor == it.rightColor && it.rightColor != it.currentColor
        }
        return cellsWithSelectedColor
    }

    private fun setRightColorForNeighborCells(neighborCells: List<Cell>) {
        for(cell in neighborCells){
            cell.currentColor = cell.rightColor
        }
    }

    private fun setGameWonIfNoUnfilledCells() {
        if(gamePicture!!.unfilledCells.size > 0){
            return
        }
        gameTimer.stop()
        gameResult = GameResult.GAME_WON
    }

    companion object {
        private const val DEFAULT_DELAY_TIME = 8000L
    }
}