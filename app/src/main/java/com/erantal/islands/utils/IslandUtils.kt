package com.erantal.islands.utils

import com.erantal.islands.models.Board

object IslandUtils {

    fun solveIslands(board: Board): Int {
        var count = 0
        val visitedArray = Array(board.getWidth()) { Array(board.getHeight()) {false} }

        board.getCells()?.let { cells ->

            cells.forEachIndexed { i, arrayOfCellModels ->
                arrayOfCellModels.forEachIndexed { j, cellModel ->
                    if (cellModel.value == 1 && !visitedArray[i][j]) {
                        scanNeighbors(board, i, j, visitedArray = visitedArray, count = count)
                        ++count
                    }
                }
            }
        }

        return count
    }

    private fun scanNeighbors(board: Board, row: Int, col: Int, visitedArray: Array<Array<Boolean>>, count: Int) {

        val rowNumber = arrayListOf(-1, -1, -1, 0, 0, 1, 1, 1)
        val columnNumber = arrayListOf(-1, 0, 1, -1, 1, -1, 0, 1)

        visitedArray[row][col] = true
        board.getCells()?.let {
            it[row][col].value = count + 1
        }

        for (direction in 0 until 8) {
            if (isSafe(board, row + rowNumber[direction], col + columnNumber[direction], visitedArray)) {
                scanNeighbors(board, row + rowNumber[direction], col + columnNumber[direction], visitedArray, count)
            }
        }
    }

    private fun isSafe(board: Board, row: Int, col: Int, visitedArray: Array<Array<Boolean>>) : Boolean{
        board.getCells()?.let { cells ->
            return (row >= 0) && (row < board.getHeight()) && (col >=0) && (col < board.getWidth()) && (cells[row][col].value == 1 && !visitedArray[row][col])
        }
        return false
    }

}