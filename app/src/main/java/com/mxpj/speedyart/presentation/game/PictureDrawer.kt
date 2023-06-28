package com.mxpj.speedyart.presentation.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.mxpj.speedyart.domain.Cell
import com.mxpj.speedyart.domain.Picture

class PictureDrawer {

    fun getPictureBitmap(picture: Picture, fitSize: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
            fitSize,
            fitSize,
            Bitmap.Config.ARGB_8888
        )
        draw(bitmap, picture, fitSize)
        return bitmap
    }

    private fun draw(bitmap: Bitmap, picture: Picture, fitSize: Int) {
        val canvas = Canvas(bitmap)
        val grid = picture.gridCells
        for(row in grid){
            drawRow(canvas, row, fitSize)
        }
    }

    private fun drawRow(canvas: Canvas, rowCells: List<Cell>, fitSize: Int) {
        for(cell in rowCells){
            drawRect(canvas, fitSize / rowCells.size, cell)
        }
    }

    private fun drawRect(canvas: Canvas, cellSize: Int, cell: Cell) {
        val rect = getRect(cellSize, cell)
        val paint = getPaint(cell)
        canvas.drawRect(rect, paint)
    }

    private fun getPaint(cell: Cell): Paint {
        val paint = Paint()
        paint.color = cell.rightColor
        return paint
    }

    private fun getRect(cellSize: Int, cell: Cell): Rect {
        val xStart = cell.xPosition * cellSize
        val yStart = cell.yPosition * cellSize
        val xEnd = xStart + cellSize
        val yEnd = yStart + cellSize
        return Rect(xStart, yEnd, xEnd, yStart)
    }
}