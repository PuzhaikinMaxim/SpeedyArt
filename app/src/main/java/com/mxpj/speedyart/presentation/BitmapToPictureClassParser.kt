package com.mxpj.speedyart.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.get
import com.mxpj.speedyart.domain.model.Cell
import com.mxpj.speedyart.domain.model.GamePicture
import javax.inject.Inject

class BitmapToPictureClassParser @Inject constructor() {

    private val colorPalette = HashMap<Int, Int>()

    private val gridCells = ArrayList<List<Cell>>()

    private val unfilledCells = HashMap<Pair<Int, Int>, Cell>()

    private val colorsAmount = HashMap<Int, Int>()

    fun parseToPicture(bitmap: Bitmap): GamePicture {
        setPictureParams(bitmap)
        return GamePicture(
            gridCells,
            getColorPaletteList(),
            unfilledCells,
            colorsAmount
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
            val cell = Cell(
                x,
                y,
                bitmapCellColorTransparent,
                bitmapCellColor,
                bitmapCellColor == 0
            )
            rowCells.add(cell)
            if(bitmapCellColor != 0){
                unfilledCells[Pair(x,y)] = cell
                colorPalette[bitmapCellColor] = bitmapCellColor
                colorsAmount[bitmapCellColor] = colorsAmount
                    .getOrDefault(bitmapCellColor,0) + 1
            }
        }
        return rowCells
    }

    private fun getColorPaletteList(): List<Int> {
        return colorPalette.map { it.value }
    }

    private fun getTransparentColor(color: Int): Int {
        if(color == 0) return 0
        return (color and 0x00FFFFFF) or 0x30000000
    }
}