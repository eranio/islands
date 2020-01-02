package com.erantal.islands.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.erantal.islands.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
    }

    private fun setClickListeners() {
        randomize_btn.setOnClickListener {
            getUserInput()?.let {
                val width = it.first
                val height = it.second
                val intent = Intent(this, IslandsActivity::class.java)
                intent.putExtra(IslandsActivity.WIDTH_KEY, width)
                intent.putExtra(IslandsActivity.HEIGHT_KEY, height)
                startActivity(intent)
            }
        }

        draw_btn.setOnClickListener {
            getUserInput()?.let {
                val width = it.first
                val height = it.second
                val intent = Intent(this, IslandsActivity::class.java)
                intent.putExtra(IslandsActivity.WIDTH_KEY, width)
                intent.putExtra(IslandsActivity.HEIGHT_KEY, height)
                intent.putExtra(IslandsActivity.BONUS_DRAW_KEY, true)
                startActivity(intent)
            }
        }
    }

    private fun getUserInput(): Pair<Int, Int>? {
        if (bitmap_size.text.isNotEmpty()) {
            val dimensions = bitmap_size.text.replace("\\s".toRegex(), "").split(",")
            if (dimensions.size == 2) {
                try {
                    val width = dimensions[0].toInt()
                    val height = dimensions[1].toInt()
                    return Pair(width, height)

                } catch (exception: Exception) {
                    Log.d(this.toString(), getString(R.string.invalid_input))
                }
            } else {
                Log.d(this.toString(), getString(R.string.invalid_input))
            }
        }
        return null
    }
}
