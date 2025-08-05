package com.example.drankingmachine

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class Info_Page : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val TitleText = ContextCompat.getString(this, R.string.title_information)
        val BarColor = ContextCompat.getColor(this, R.color.sub_color)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(BarColor))
        supportActionBar?.setTitle(TitleText)

        val ScrollPage = ScrollView(this)
        ScrollPage.setPadding(20, 20, 20, 20)
        val ShowText = TextView(this).apply {
            textSize = 20f
            setTextColor(Color.BLACK)
        }

        val inputStream = assets.open("Information.txt")
        val content = inputStream.bufferedReader().use { it.readText() }
        ShowText.text = content

           ScrollPage.addView(ShowText)
        setContentView(ScrollPage)
        }
}