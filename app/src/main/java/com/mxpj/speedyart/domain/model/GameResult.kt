package com.mxpj.speedyart.domain.model

import com.mxpj.speedyart.R

enum class GameResult(val msg: Int? = null) {
    GAME_WON(R.string.game_won_message),
    GAME_LOST(R.string.game_lost_message),
    GAME_CONTINUING()
}