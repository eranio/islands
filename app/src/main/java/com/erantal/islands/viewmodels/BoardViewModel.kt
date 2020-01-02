package com.erantal.islands.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erantal.islands.models.Board
import com.erantal.islands.models.CellModel
import com.erantal.islands.utils.IslandUtils
import kotlin.random.Random

class BoardViewModel : ViewModel() {

    private val boardLiveData = MutableLiveData<Board>()
    private val islandsCountLiveData = MutableLiveData<Int>()
    private lateinit var board: Board
    private lateinit var array: Array<Array<CellModel>>
    private var islandsCount = 0

    fun init(width: Int, height: Int, bonusDraw: Boolean) {

        array = Array(width) { Array(height) { CellModel(0) } }
        board = Board()

        for (i in 0 until width)
            for (j in 0 until height) {
                if (bonusDraw) {
                    array[i][j] = CellModel(0)
                } else {
                    // this code generates numbers from 0 to 4, where 1-4 will be converted to 'Empty cell' (0) and 0 will be black cell
                    array[i][j] = when (Random.nextInt(0, 4)) {
                        0 -> CellModel(1)
                        else -> CellModel(0)
                    }
                }
            }

        board.setCells(array)
        boardLiveData.postValue(board)
    }

    fun getBoardLiveData(): MutableLiveData<Board> {
        return boardLiveData
    }

    fun getIslandsCountLiveData(): MutableLiveData<Int> {
        return islandsCountLiveData
    }

    fun onSolveClicked() {
        islandsCount = IslandUtils.solveIslandsB(board)
        board.setIsSolved(true)
        islandsCountLiveData.postValue(islandsCount)
    }

}
