package com.mxpj.speedyart.domain

import com.mxpj.speedyart.domain.model.GameColorsData
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.Picture

interface GameControllerObserver {

    fun onPictureChange(picture: Picture)

    fun onHealthAmountChange(healthAmount: Int)

    fun onGameColorsDataChange(gameColorsData: GameColorsData)

    fun onGameResultChange(gameResult: GameResult)

    fun onTimerReset()

    fun onTimerStop()
}