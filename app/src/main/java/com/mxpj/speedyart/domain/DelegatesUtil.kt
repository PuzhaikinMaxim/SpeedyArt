package com.mxpj.speedyart.domain

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

public inline fun <T> observe(
    initialValue: T,
    crossinline onChange: (newValue: T) -> Unit
): ReadWriteProperty<Any?, T> {
    return Delegates.observable(initialValue) {
            _, _, newValue ->
        onChange(newValue)
    }
}