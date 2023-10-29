package com.mxpj.speedyart.presentation.game

import kotlinx.coroutines.*
import javax.inject.Inject

class SimpleTimer @Inject constructor() {

    private val scope = CoroutineScope(Dispatchers.Main)

    var amountOfSecondsPassed = 0
    private set

    fun stopTimer() {
        scope.cancel()
    }

    fun startTimer() {
        amountOfSecondsPassed = 0
        scope.launch {
            for(i in 0..1000){
                amountOfSecondsPassed = i
                delay(1000)
            }
        }
    }
}