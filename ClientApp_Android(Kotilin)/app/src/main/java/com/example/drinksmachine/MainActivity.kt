package com.example.drinksmachine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.drinksmachine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var ObjectBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        ObjectBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ObjectBinding.root)

//        window.decorView.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(ObjectBinding.fragmentContainer.id, HomeFragment()) // 預設頁面
                .commit()

        }
    }
}