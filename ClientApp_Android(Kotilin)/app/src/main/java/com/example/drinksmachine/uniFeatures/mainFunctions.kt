package com.example.drinksmachine.uniFeatures

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.drinksmachine.*

/**
 * Fragment換頁工程
 */
fun Fragment.switchFragment(fragment: Fragment, containerId: Int = R.id.fragment_container) {
    parentFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(null)
        .commit()
    Log.v("myFragmentCheck", "即時切換 - resumed fragment: ${fragment::class.java.simpleName}")
}

object ActionBarHelper {
    fun updateActionBar(activity: AppCompatActivity, frag: Fragment) {
        when (frag) {
            is MainPage, is OptionFragment , is Finish_Page -> {
                activity.supportActionBar?.hide()
            }
            else -> {
                activity.supportActionBar?.apply {
                    // 隱藏預設標題
                    setDisplayShowTitleEnabled(false)
                    // 顯示 ActionBar
                    show()
                    // 設置背景
                    setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.action_bar))

                    // 建立置中標題 TextView
                    val textView = TextView(activity).apply {
                        text = when (frag) {
                            is History_Page -> activity.getString(R.string.title_history)
                            is SettingPage -> activity.getString(R.string.title_setting)
                            is Info_Page -> activity.getString(R.string.title_information)
                            is DiyPage -> activity.getString(R.string.title_diyPage)
                            else -> ""
                        }
                        textSize = 30f // 可調整字型大小
                        setTextColor(Color.WHITE)
                        gravity = Gravity.CENTER
                    }

                    // 將自訂 TextView 放到 ActionBar
                    val params = ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    )
                    params.gravity = Gravity.CENTER
                    customView = textView
                    setDisplayShowCustomEnabled(true)
                }
            }
        }
    }
}