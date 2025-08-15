package com.example.drinksmachine

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.drinksmachine.databinding.ActivityMainPageBinding
import com.example.drinksmachine.uniFeatures.MyDialog


class MainPage : AppCompatActivity() {

    private lateinit var ObjectBinging: ActivityMainPageBinding
    private var  clickCount = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ObjectBinging = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(ObjectBinging.root)
        supportActionBar?.hide()

        /**
         * SettingPage換成要測的當首頁
         */
//        startActivity(Intent(this, DiyPage::class.java))
//        finish()

        //頁面轉跳區
        val buttonListener = View.OnClickListener { view ->
            when (view) {
                ObjectBinging.logoIcon -> {
                    clickCount++
                    if (clickCount == 10){
                        val dialog = MyDialog(this)
                            .setTitle("關閉畫面中～ 密碼?")
                            .setBackgroundColor(ContextCompat.getColor(this, R.color.main_color))
                            .setMidButtonVisible(false)
                        val editText = EditText(this).apply {
                            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                            setPadding(35, 35, 35, 10)
                            background = null
                            hint = "Password"
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        }
                            dialog.setView(editText)
                        dialog.setButtonR(getString(R.string.confirm_button), Color.RED) {
                            val pass = editText.text.toString()
                            if( pass == "happyEnding") throw RuntimeException("App Crash") else Toast.makeText(this, "Password ❌",Toast.LENGTH_SHORT).show()
                        }
                        dialog.setButtonL(getString(R.string.exit), Color.BLACK){
                            clickCount = 0
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                }
                ObjectBinging.DiyButton -> startActivity(Intent(this, DiyPage::class.java))
                ObjectBinging.HistoryButton -> startActivity(Intent(this, History_Page::class.java))
                ObjectBinging.LoginButton -> startActivity(Intent(this, Login_Page::class.java))
                ObjectBinging.SignButton -> startActivity(Intent(this, SignUp_Page::class.java))
                ObjectBinging.SettingButton -> startActivity(Intent(this, SettingPage::class.java))
                ObjectBinging.InfoButton -> startActivity(Intent(this, Info_Page::class.java))
            }
        }
        ObjectBinging.logoIcon.setOnClickListener(buttonListener)
        ObjectBinging.DiyButton.setOnClickListener(buttonListener)
        ObjectBinging.RandomButton.setOnClickListener(buttonListener)
        ObjectBinging.HistoryButton.setOnClickListener(buttonListener)
        ObjectBinging.SignButton.setOnClickListener(buttonListener)
        ObjectBinging.LoginButton.setOnClickListener(buttonListener)
        ObjectBinging.InfoButton.setOnClickListener(buttonListener)
        ObjectBinging.SettingButton.setOnClickListener(buttonListener)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

}