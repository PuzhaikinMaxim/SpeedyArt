package com.mxpj.speedyart.domain

import com.mxpj.speedyart.domain.model.GameColorsData
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.GamePicture

interface GameControllerObserver {

    fun onPictureChange(gamePicture: GamePicture)

    fun onHealthAmountChange(healthAmount: Int)

    fun onGameColorsDataChange(gameColorsData: GameColorsData)

    fun onGameResultChange(gameResult: GameResult)

    fun onTimerReset()

    fun onTimerStop()
}