package com.example.drinksmachine

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Finish_Page : AppCompatActivity() {

    private lateinit var ImageShow : ImageView
    private lateinit var TextShow : TextView
    private lateinit var HomeButtom : Button
    private lateinit var ProgressBar: ProgressBar
    private var progress = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()

        setContentView(R.layout.activity_finish_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ProgressBar = findViewById(R.id.ProgressBar)
        TextShow = findViewById(R.id.ProgressTextShow)
        ImageShow = findViewById(R.id.ProgressImageShow)
        HomeButtom = findViewById(R.id.HomePage_Button)
        HomeButtom.visibility = View.INVISIBLE
        startProgress()
        HomeButtom.setOnClickListener {
            startActivity(Intent(this, MainPage::class.java))
        }
    }

    private fun startProgress() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress <= 100) {
                    ProgressBar.progress = progress
                    TextShow.text = getString(R.string.progressPreparing_text) + "$progress%"
                    progress += 10
                    handler.postDelayed(this, 500) // 每秒更新
                }
                else {
                    HomeButtom.visibility = View.VISIBLE
                    TextShow.text = getString(R.string.progressFinish_text)
                    ImageShow.setImageResource(R.drawable.finish)
                }
            }
        }, 2500)
    }
}

