package com.mxpj.speedyart.domain

import kotlinx.coroutines.*

class GameTimer(
    private val gameControllerObserver: GameControllerObserver,
    private val onTimerStop: () -> Unit,
) {
    private val coroutine = CoroutineScope(Dispatchers.IO)

    private var delayTime: Long = DEFAULT_DELAY_TIME

    private lateinit var timerJob: Job

    fun start() {
        timerJob = coroutine.launch {
            delay(delayTime)
            onTimerStop()
        }
    }

    fun setDelayTime(newDelayTime: Long) {
        delayTime = newDelayTime
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

    companion object {
        private const val DEFAULT_DELAY_TIME = 8000L
    }
}