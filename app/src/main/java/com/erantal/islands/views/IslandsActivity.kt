package com.erantal.islands.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.erantal.islands.R
import com.erantal.islands.viewmodels.BoardViewModel
import kotlinx.android.synthetic.main.islands.*

class IslandsActivity : AppCompatActivity() {

    companion object {
        const val WIDTH_KEY = "width"
        const val HEIGHT_KEY = "height"
        const val BONUS_DRAW_KEY = "bonus_draw"
    }

    private lateinit var boardViewModel: BoardViewModel
    private var islandSolved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.islands)
        initViewModel()
        setClickListeners()
    }

    private fun initViewModel() {
        val width = intent.getIntExtra(WIDTH_KEY, 0)
        val height = intent.getIntExtra(HEIGHT_KEY, 0)
        val bonusDraw = intent.getBooleanExtra(BONUS_DRAW_KEY, false)
        boardViewModel = ViewModelProviders.of(this).get(BoardViewModel::class.java)
        boardViewModel.init(width, height, bonusDraw)
        boardViewModel.getBoardLiveData().observe(this, Observer {
            bitmapView.init(width, height, it, bonusDraw)
            bitmapView.invalidate()
        })

        boardViewModel.getIslandsCountLiveData().observe(this, Observer { count ->
            result.text = getString(R.string.result_text, count)
            bitmapView.refreshColors()
            islandSolved = true
            solve_btn.text = getString(R.string.restart)
        })
    }

    private fun setClickListeners() {
        solve_btn.setOnClickListener {
            if (islandSolved) {
                onBackPressed()
            } else {
                boardViewModel.onSolveClicked()
            }
        }
    }

}