package com.mxpj.speedyart.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.get
import com.mxpj.speedyart.domain.Cell
import com.mxpj.speedyart.domain.Picture

class ImageToPictureClassParser {

    private val colorPalette = HashMap<Int, Int>()

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
            val bitmapCellColor = bitmap[x, y]
            val bitmapCellColorTransparent = getTransparentColor(bitmapCellColor)
            rowCells.add(
                Cell(
                    x,
                    y,
                    bitmapCellColorTransparent,
                    bitmapCellColor,
                    bitmapCellColor == 0
                )
            )
            if(bitmapCellColor != 0){
                //println("bitmap color: $bitmapCellColor")
                colorPalette[bitmapCellColor] = bitmapCellColor
            }
        }
        return rowCells
    }

    private fun getColorPaletteList(): List<Int> {
        return colorPalette.map { it.value }
    }

    private fun getTransparentColor(color: Int): Int {
        if(color == 0) return 0
        return (color and 0x00FFFFFF) or 0x70000000
    }
}