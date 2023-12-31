package com.mxpj.speedyart.presentation.game

import android.graphics.*
import androidx.compose.ui.graphics.toArgb
import com.mxpj.speedyart.domain.model.AppTheme
import com.mxpj.speedyart.domain.model.Cell
import com.mxpj.speedyart.domain.model.GamePicture
import com.mxpj.speedyart.ui.theme.DarkThemeBackground

class PictureDrawer(
    private val gamePicture: GamePicture,
    private val selectedColor: Int? = null,
    private val fitSize: Int,
    private val theme: AppTheme = AppTheme.LIGHT
) {

    fun getPictureBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(
            fitSize,
            fitSize,
            Bitmap.Config.ARGB_8888
        )
        draw(bitmap)
        return bitmap
    }

    private fun draw(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        val grid = gamePicture.gridCells
        drawBackground(canvas)
        for(row in grid){
            drawRow(canvas, row)
        }
        drawGrid(canvas, fitSize / grid[0].size)
    }

    private fun drawBackground(canvas: Canvas) {
        val backgroundColor = getBackgroundColorCode()
        canvas.drawRect(getRectForBackground(canvas),getPaint(backgroundColor))
    }

    private fun getRectForBackground(canvas: Canvas) = Rect(
        0,0,canvas.width,canvas.height
    )

    private fun drawRow(canvas: Canvas, rowCells: List<Cell>) {
        for(cell in rowCells){
            drawRect(canvas, fitSize / rowCells.size, cell)
        }
    }

    private fun drawRect(canvas: Canvas, cellSize: Int, cell: Cell) {
        val rect = getRect(cellSize, cell)
        val paint = getPaintForCell(cell)
        canvas.drawRect(rect, paint)
    }

    private fun drawGrid(canvas: Canvas, cellSize: Int) {
        val gridLinesAmount = fitSize / cellSize
        for(i in 0..gridLinesAmount){
            val gridColor = getGridColorCode()
            canvas.drawLine(
                0f,
                i * cellSize.toFloat(),
                fitSize.toFloat(),
                i * cellSize.toFloat(),
                getPaint(gridColor)
            )
            canvas.drawLine(
                i * cellSize.toFloat(),
                0f,
                i * cellSize.toFloat(),
                fitSize.toFloat(),
                getPaint(gridColor)
            )
        }
    }

    private fun getPaint(paintColor: Int): Paint {
        return Paint().apply {
            color = paintColor
        }
    }

    private fun getPaintForCell(cell: Cell): Paint {
        val paint = Paint()
        if(
            selectedColor != null &&
            cell.currentColor != cell.rightColor &&
            selectedColor == cell.rightColor
        ){
            return getPaint(Color.GRAY)
        }
        paint.color = cell.currentColor
        return paint
    }

    private fun getRect(cellSize: Int, cell: Cell): Rect {
        val xStart = cell.xPosition * cellSize
        val yStart = cell.yPosition * cellSize
        val xEnd = xStart + cellSize
        val yEnd = yStart + cellSize
        return Rect(xStart, yEnd, xEnd, yStart)
    }

    private fun getGridColorCode(): Int {
        return if(theme == AppTheme.LIGHT) Color.BLACK else Color.WHITE
    }

    private fun getBackgroundColorCode(): Int {
        return if(theme == AppTheme.LIGHT) Color.WHITE else DarkThemeBackground.toArgb()
    }
}