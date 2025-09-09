package com.example.drinksmachine.uniFeatures

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.fragment.app.Fragment
import androidx.transition.Visibility
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
fun setFragmentTitle(activity: AppCompatActivity, fragment: Fragment) {
    val actionBarTitle_LL = activity.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.actionBarTitle_LL)
    val actionBarTitle = activity.findViewById<TextView>(R.id.actionBarTitle)
    val leftButton = activity.findViewById<ImageButton>(R.id.leftButton)
    val rightButton = activity.findViewById<ImageButton>(R.id.rightButton)

    val fragmentName = fragment::class.java.simpleName

    if (fragmentName=="MainPage"|| fragmentName=="Finish_Page"){
        actionBarTitle_LL.visibility = View.GONE
    }else{
        actionBarTitle_LL.visibility = View.VISIBLE
        actionBarTitle.text = fragmentName
    }

    leftButton.visibility = View.VISIBLE
    leftButton.setOnClickListener { activity.supportFragmentManager.popBackStack() }
    rightButton.visibility = View.GONE

}