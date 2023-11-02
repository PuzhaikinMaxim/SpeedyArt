package com.mxpj.speedyart.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import javax.inject.Inject

class GameCountdown @Inject constructor() {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val _timer = MutableLiveData(COUNTDOWN_INITIAL_VALUE)
    val timer: LiveData<Int>
        get() = _timer

    fun startCountdown(onCountdownEnd: () -> Unit) {
        scope.launch {
            delay(1000)
            for(count in 3 downTo 1){
                _timer.postValue(count)
                delay(1000)
            }
            withContext(Dispatchers.Main){
                onCountdownEnd()
            }
        }
    }

    fun stopCountdown() {
        scope.cancel()
    }

    companion object {
        private const val COUNTDOWN_INITIAL_VALUE = 3
    }
}