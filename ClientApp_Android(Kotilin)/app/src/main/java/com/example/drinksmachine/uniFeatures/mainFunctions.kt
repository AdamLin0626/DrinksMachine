package com.example.drinksmachine.uniFeatures

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
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
                activity.supportActionBar?.show()
                activity.supportActionBar?.title = frag.javaClass.simpleName
            }
        }
    }
}