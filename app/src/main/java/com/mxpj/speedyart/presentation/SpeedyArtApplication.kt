package com.mxpj.speedyart.presentation

import android.app.Application

class SpeedyArtApplication: Application() {

    init {
        PixelImageProvider.setApplicationContext(this)
    }
}