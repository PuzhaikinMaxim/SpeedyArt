package com.mxpj.speedyart.presentation

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mxpj.speedyart.R

val FontFamily.Companion.Silver: FontFamily
    get() = FontFamily(arrayListOf<Font>().apply {
        add(Font(R.font.silver))
    })


fun FontFamily.Companion.getSilverFont(): FontFamily {
    return FontFamily(arrayListOf<Font>().apply {
        add(Font(R.font.silver))
    })
}