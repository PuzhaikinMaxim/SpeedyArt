package com.mxpj.speedyart.domain

import kotlinx.coroutines.*

class GameTimer(
    private val delayTime: Long,
    private val gameControllerObserver: GameControllerObserver,
    private val onTimerStop: () -> Unit,
) {

    private val coroutine = CoroutineScope(Dispatchers.IO)

    private lateinit var timerJob: Job

    fun start() {
        timerJob = coroutine.launch {
            delay(delayTime)
            onTimerStop()
        }
    }

    fun reset() {
        stop()
        start()
        gameControllerObserver.onTimerReset()
    }

    fun stop() {
        timerJob.cancel()
        //gameControllerObserver.onTimerStop()
    }
}