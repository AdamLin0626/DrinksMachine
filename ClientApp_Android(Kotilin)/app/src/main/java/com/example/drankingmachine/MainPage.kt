package com.example.drankingmachine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private lateinit var DiyButton: Button
private lateinit var RandomButton: Button
private lateinit var HistoryButton: Button
private lateinit var LoginButton: Button
private lateinit var SignButton: Button
private lateinit var SettingButton: Button
private lateinit var InfoButton: Button

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        supportActionBar?.hide()

        //定義元件
        DiyButton = findViewById(R.id.DiyButton)
        RandomButton = findViewById(R.id.RandomButton)
        HistoryButton = findViewById(R.id.HistoryButton)
        LoginButton = findViewById(R.id.LoginButton)
        SignButton = findViewById(R.id.SignButton)
        SettingButton = findViewById(R.id.SettingButton)
        InfoButton = findViewById(R.id.InfoButton)

        //頁面轉跳區
        val BtnListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.DiyButton -> startActivity(Intent(this, DiyPage_First::class.java))
                R.id.HistoryButton -> startActivity(Intent(this, Historty_Page::class.java))
                R.id.LoginButton -> startActivity(Intent(this, Login_Page::class.java))
                R.id.SignButton -> startActivity(Intent(this, SignUp_Page::class.java))
                R.id.SettingButton -> startActivity(Intent(this, Setting_Page::class.java))
                R.id.InfoButton -> startActivity(Intent(this, Info_Page::class.java))
            }
        }
        DiyButton.setOnClickListener(BtnListener)
        RandomButton.setOnClickListener(BtnListener)
        HistoryButton.setOnClickListener(BtnListener)
        SignButton.setOnClickListener(BtnListener)
        LoginButton.setOnClickListener(BtnListener)
        InfoButton.setOnClickListener(BtnListener)
        SettingButton.setOnClickListener(BtnListener)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}