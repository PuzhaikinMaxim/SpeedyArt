package com.mxpj.speedyart.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.get
import com.mxpj.speedyart.domain.Cell
import com.mxpj.speedyart.domain.Picture

class ImageToPictureClassParser {

    private val colorPalette = HashMap<String, String>()

    private val gridCells = ArrayList<List<Cell>>()

    private val unfilledCells = HashMap<Pair<Int, Int>, Cell>()

    fun parseToPicture(bitmap: Bitmap): Picture {
        setPictureParams(bitmap)
        return Picture(
            gridCells,
            getColorPaletteList(),
            unfilledCells
        )
    }

    fun getBitmapFactoryOptions(): BitmapFactory.Options {
        val options = BitmapFactory.Options()
        options.inScaled = false
        return options
    }

    private fun setPictureParams(bitmap: Bitmap) {
        for(y in 0 until bitmap.height){
            val parsedRow = parseRow(bitmap, y)
            gridCells.add(parsedRow)
        }
    }

    private fun parseRow(bitmap: Bitmap, y: Int): List<Cell> {
        val rowCells = ArrayList<Cell>()
        for(x in 0 until bitmap.width){
            val bitmapCell = bitmap[x, y]
            rowCells.add(
                Cell(x,y, bitmapCell, bitmapCell, bitmapCell == 0)
            )
        }
        return rowCells
    }

    private fun getColorPaletteList(): List<String> {
        return colorPalette.map { it.value }
    }
}