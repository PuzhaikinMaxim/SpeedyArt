package com.mxpj.speedyart.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpeedyArtApplication: Application() {

    init {
        PixelImageProvider.setApplicationContext(this)
    }
}