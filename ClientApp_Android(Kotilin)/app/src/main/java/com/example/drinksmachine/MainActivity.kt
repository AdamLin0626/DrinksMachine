package com.example.drinksmachine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.drinksmachine.databinding.ActivityMainBinding
import com.example.drinksmachine.uniFeatures.ActionBarHelper

class MainActivity : AppCompatActivity() {
    private lateinit var Binding: ActivityMainBinding

    private val handler = Handler(Looper.getMainLooper())
    private val checkIntervalMs = 30_000L // 30秒

    // 每 30 秒檢查一次
    private val checkRunnable = object : Runnable {
        override fun run() {
            val current = getCurrentFragment()
            Log.v("FragmentCheck", "定時檢查 - 目前 fragment: ${currentName(current)}")
            handler.postDelayed(this, checkIntervalMs)
        }
    }

    private val lifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentResumed(fm: FragmentManager, frag: Fragment) {
            super.onFragmentResumed(fm, frag)
            ActionBarHelper.updateActionBar(this@MainActivity, frag)    // 即時監聽 Fragment 切換 (resume)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 先隱藏，因為進入時會是 MainPage
        supportActionBar?.hide()
        Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        // 啟動定時檢查
        handler.post(checkRunnable)

        // 註冊 lifecycle callbacks
        supportFragmentManager.registerFragmentLifecycleCallbacks(lifecycleCallbacks, true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(Binding.fragmentContainer.id, MainPage()) // 預設頁面
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(checkRunnable)
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(lifecycleCallbacks)
    }

    // 取得目前顯示的 fragment
    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(Binding.fragmentContainer.id)
            ?: supportFragmentManager.primaryNavigationFragment
    }

    private fun currentName(f: Fragment?): String {
        return f?.javaClass?.simpleName ?: "none"
    }
}
