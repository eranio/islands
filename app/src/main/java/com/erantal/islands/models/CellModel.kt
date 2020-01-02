package com.erantal.islands.models

class CellModel(var value: Int) {

    init {
        this.value = value
    }

    private var color: Int? = null

    fun setColor(color: Int) {
        this.color = color
    }
}