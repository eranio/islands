package com.erantal.islands.utils

import com.erantal.islands.models.Board

object IslandUtils {

    fun solveIslandsB(board: Board): Int {
        var count = 0
        val visitedArray = Array(board.getWidth()) { Array(board.getHeight()) {false} }

        board.getCells()?.let { cells ->

            cells.forEachIndexed { i, arrayOfCellModels ->
                arrayOfCellModels.forEachIndexed { j, cellModel ->
                    if (cellModel.value == 1 && !visitedArray[i][j]) {
                        scanNeighborsB(board, i, j, visitedArray = visitedArray, count = count)
                        ++count
                    }
                }
            }
        }

        return count
    }


    fun countIslands(array: Array<Array<Int>>): Int {
        var count = 0
        val visitedArray = Array(array.size) { Array(array[0].size) {false} }


        for (i in array.indices) {
            for (j in array[0].indices) {
                if (array[i][j] == 1 && !visitedArray[i][j]) {
                    scanNeighbors(array, i, j, visitedArray = visitedArray, count = count)
                    ++count
                }
            }
        }

        return count
    }

    private fun scanNeighborsB(board: Board, row: Int, col: Int, visitedArray: Array<Array<Boolean>>, count: Int) {

        val rowNumber = arrayListOf(-1, -1, -1, 0, 0, 1, 1, 1)
        val columnNumber = arrayListOf(-1, 0, 1, -1, 1, -1, 0, 1)

        visitedArray[row][col] = true
        board.getCells()?.let {
            it[row][col].value = count + 1
        }

        for (direction in 0 until 8) {
            if (isSafeB(board, row + rowNumber[direction], col + columnNumber[direction], visitedArray)) {
                scanNeighborsB(board, row + rowNumber[direction], col + columnNumber[direction], visitedArray, count)
            }
        }
    }

    private fun scanNeighbors(array: Array<Array<Int>>, row: Int, col: Int, visitedArray: Array<Array<Boolean>>, count: Int) {

        val rowNumber = arrayListOf(-1, -1, -1, 0, 0, 1, 1, 1)
        val columnNumber = arrayListOf(-1, 0, 1, -1, 1, -1, 0, 1)

        visitedArray[row][col] = true
        array[row][col] = count + 1

        for (direction in 0 until 8) {
            if (isSafe(array, row + rowNumber[direction], col + columnNumber[direction], visitedArray)) {
                scanNeighbors(array, row + rowNumber[direction], col + columnNumber[direction], visitedArray, count)
            }
        }
    }

    private fun isSafeB(board: Board, row: Int, col: Int, visitedArray: Array<Array<Boolean>>) : Boolean{
        board.getCells()?.let { cells ->
            return (row >= 0) && (row < board.getHeight()) && (col >=0) && (col < board.getWidth()) && (cells[row][col].value == 1 && !visitedArray[row][col])
        }
        return false
    }

    private fun isSafe(array: Array<Array<Int>>, row: Int, col: Int, visitedArray: Array<Array<Boolean>>) : Boolean{
        return (row >= 0) && (row < array.size) && (col >=0) && (col < array[0].size) && (array[row][col] == 1 && !visitedArray[row][col])
    }

}