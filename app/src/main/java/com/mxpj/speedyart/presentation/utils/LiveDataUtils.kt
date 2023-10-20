package com.mxpj.speedyart.presentation.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <T> LiveData<T>.observeStateChange(onStateChange: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { onStateChange(it) }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
}

@Composable
fun <T> LiveData<T>.observeAsState(onStateChange: () -> Unit): State<T?> = observeAsState(value, onStateChange)

@Composable
fun <R, T : R> LiveData<T>.observeAsState(initial: R, onStateChange: () -> Unit): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> {
            state.value = it
            onStateChange()
        }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}