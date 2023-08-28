package com.mxpj.speedyart.presentation

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class PixelImageProvider {

    companion object {

        private var context: Application? = null

        fun setApplicationContext(context: Application) {
            if(this.context != null){
                return
            }
            this.context = context
        }

        fun getPixelImageBitmap(resource: Int): ImageBitmap {
            if(context == null) throw RuntimeException("Application is null")
            val picture = BitmapFactory.decodeResource(
                context!!.resources,
                resource,
                getBitmapFactoryOptions()
            )
            return picture.asImageBitmap()
        }

        fun getPixelBitmap(resource: Int): Bitmap {
            if (context == null) throw RuntimeException("Application is null")
            return BitmapFactory.decodeResource(
                context!!.resources,
                resource,
                getBitmapFactoryOptions()
            )
        }

        private fun getBitmapFactoryOptions(): BitmapFactory.Options {
            val options = BitmapFactory.Options()
            options.inScaled = false
            return options
        }
    }
}