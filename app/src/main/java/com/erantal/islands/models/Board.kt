package com.erantal.islands.models

class Board {
    private var is_solved = false
    private var cells: Array<Array<CellModel>>? = null
    private var width = 0
    private var height = 0

    fun setCells(cells: Array<Array<CellModel>>) {
        this.cells = cells
        this.width = cells.size
        this.height = cells[0].size
    }

    fun getCells() : Array<Array<CellModel>>? {
        return cells
    }

    fun getWidth() : Int {
        return width
    }

    fun getHeight() : Int {
        return height
    }

    fun setIsSolved(solved: Boolean) {
        this.is_solved = solved
    }
}