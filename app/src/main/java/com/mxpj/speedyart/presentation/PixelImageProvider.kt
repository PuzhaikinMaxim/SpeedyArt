package com.mxpj.speedyart.presentation

import android.app.Application
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.mxpj.speedyart.R

class PixelImageProvider {

    companion object {

        private var context: Application? = null

        fun setApplicationContext(context: Application) {
            if(this.context != null){
                return
            }
            this.context = context
        }

        fun getPixelImageBitmap(resource: Int): ImageBitmap{
            if(context == null) throw RuntimeException("Application is null")
            with(context){
                val picture = BitmapFactory.decodeResource(
                    context!!.resources,
                    resource,
                    getBitmapFactoryOptions()
                )
                return picture.asImageBitmap()
            }
        }

        private fun getBitmapFactoryOptions(): BitmapFactory.Options {
            val options = BitmapFactory.Options()
            options.inScaled = false
            return options
        }
    }
}