package com.erantal.islands.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.erantal.islands.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
    }

    override fun onStart() {
        super.onStart()
        bitmap_size.text.clear()
    }

    private fun setClickListeners() {
        randomize_btn.setOnClickListener {
            getUserInput()?.let {
                openIslandActivity(it, false)
            }
        }

        draw_btn.setOnClickListener {
            getUserInput()?.let {
                openIslandActivity(it, true)
            }
        }
    }

    private fun openIslandActivity(dimensions: Pair<Int, Int>, bonus: Boolean) {
        val width = dimensions.first
        val height = dimensions.second
        val intent = Intent(this, IslandsActivity::class.java)
        intent.putExtra(IslandsActivity.WIDTH_KEY, width)
        intent.putExtra(IslandsActivity.HEIGHT_KEY, height)
        intent.putExtra(IslandsActivity.BONUS_DRAW_KEY, bonus)
        startActivity(intent)
    }

    private fun getUserInput(): Pair<Int, Int>? {
        if (bitmap_size.text.isNotEmpty()) {
            val dimensions = bitmap_size.text.replace("\\s".toRegex(), "").split(",")
            if (dimensions.size == 2) {
                try {
                    val width = dimensions[0].toInt()
                    val height = dimensions[1].toInt()
                    showErrorMessage(false)
                    return Pair(width, height)

                } catch (exception: Exception) {
                    showErrorMessage(true)
                }
            } else {
                showErrorMessage(true)
            }
        }
        showErrorMessage(true)
        return null
    }

    private fun showErrorMessage(show: Boolean) {
        error_message.visibility = when (show) {
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }
        if (show) {
            Log.d(this.toString(), getString(R.string.invalid_input))
        }
    }
}
