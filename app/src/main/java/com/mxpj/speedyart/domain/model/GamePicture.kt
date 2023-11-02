package com.mxpj.speedyart.domain.model

data class GamePicture(
    val gridCells: List<List<Cell>>,
    val availablePalette: List<Int>,
    val unfilledCells: HashMap<Pair<Int, Int>, Cell>,
    val colorsAmount: HashMap<Int, Int>
) {

    fun getCell(xPosition: Int, yPosition: Int): Cell {
        if(xPosition >= gridCells[0].size || yPosition >= gridCells.size) {
            return getDefaultCell()
        }
        return gridCells[yPosition][xPosition]
    }

    companion object {

        private fun getDefaultCell(): Cell {
            return Cell(
                -1,
                -1,
                -1,
                -1,
                false
            )
        }
    }
}