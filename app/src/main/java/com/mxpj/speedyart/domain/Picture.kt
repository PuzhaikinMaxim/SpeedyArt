package com.mxpj.speedyart.domain

data class Picture(
    val gridCells: List<List<Cell>>,
    val availablePalette: List<Int>,
    val unfilledCells: HashMap<Pair<Int, Int>, Cell>
) {

    fun getCell(xPosition: Int, yPosition: Int): Cell {
        return gridCells[yPosition][xPosition]
    }
}