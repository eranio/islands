package com.erantal.islands.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.erantal.islands.models.Board
import java.util.*

class BitmapView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private var numColumns = 0
    private var numRows = 0
    private var cellWidth = 0
    private var cellHeight = 0
    private var bonusDraw = false
    private val blackPaint = Paint()
    private val cellPaint = Paint()
    private var cellChecked: Array<BooleanArray>? = null
    private var board: Board? = null
    private var lastState = true
    private val colorsMap = HashMap<Int, Int>()
    private var useColor = false

    init {
        blackPaint.style = Paint.Style.FILL_AND_STROKE
        cellPaint.style = Paint.Style.FILL_AND_STROKE
    }

    private fun setNumColumns(numColumns: Int) {
        this.numColumns = numColumns
        setDimensions()
    }

    private fun setNumRows(numRows: Int) {
        this.numRows = numRows
        setDimensions()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setDimensions()
    }

    private fun setDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return
        }
        cellWidth = 40
        cellHeight = 40
        cellChecked = Array(
            numColumns
        ) { BooleanArray(numRows) }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        if (numColumns == 0 || numRows == 0) {
            return
        }
        val width = numColumns * cellWidth
        val height = numRows * cellHeight
        for (i in 0 until numColumns) {
            for (j in 0 until numRows) {
                var isChecked = false
                if (board != null && board!!.getCells() != null) {
                    val colorKey = board!!.getCells()!![i][j].value
                    isChecked = colorKey > 0
                    if (isChecked && useColor) {
                        if (colorsMap.containsKey(colorKey)) {
                            val islandColor = colorsMap[colorKey] as Int
                            cellPaint.color = islandColor
                        } else {
                            val newColor = Color.rgb(
                                (Math.random() * 255).toInt(),
                                (Math.random() * 255).toInt(),
                                (Math.random() * 255).toInt()
                            )
                            cellPaint.color = newColor
                            board!!.getCells()!![i][j].setColor(newColor)
                            colorsMap[colorKey] = newColor
                        }
                    }
                }
                cellChecked?.let {
                    if (it[i][j]) {
                        isChecked = true
                    }
                }
                if (isChecked) {
                    canvas.drawRect(
                        i * cellWidth.toFloat(), j * cellHeight.toFloat(),
                        (i + 1) * cellWidth.toFloat(), (j + 1) * cellHeight.toFloat(),
                        cellPaint
                    )
                }
            }
        }
        for (i in 0..numColumns) {
            canvas.drawLine(
                i * cellWidth.toFloat(),
                0f,
                i * cellWidth.toFloat(),
                height.toFloat(),
                blackPaint
            )
        }
        for (i in 0..numRows) {
            canvas.drawLine(
                0f,
                i * cellHeight.toFloat(),
                width.toFloat(),
                i * cellHeight.toFloat(),
                blackPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!bonusDraw) return false
        cellChecked?.let { cellChecked ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val column = (event.x / cellWidth).toInt()
                val row = (event.y / cellHeight).toInt()
                if (column > numColumns - 1 || row > numRows - 1) return false
                cellChecked[column][row] = !cellChecked[column][row]
                board!!.getCells()!![column][row]
                    .value = if (cellChecked[column][row]) 1 else 0
                lastState = cellChecked[column][row]
                invalidate()
            } else if (event.action == MotionEvent.ACTION_MOVE) {
                val column = (event.x / cellWidth).toInt()
                val row = (event.y / cellHeight).toInt()
                cellChecked[column][row] = lastState
                board!!.getCells()!![column][row]
                    .value = if (cellChecked[column][row]) 1 else 0
                invalidate()
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(numColumns * cellWidth, numRows * cellHeight)
    }

    fun init(width: Int, height: Int, board: Board?, bonus_draw: Boolean) {
        setNumColumns(width)
        setNumRows(height)
        this.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        this.bonusDraw = bonus_draw
        this.board = board
        invalidate()
    }

    fun refreshColors() {
        useColor = true
        invalidate()
    }

}