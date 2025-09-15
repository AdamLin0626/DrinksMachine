package com.example.drinksmachine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.drinksmachine.databinding.ActivityFinishPageBinding
import com.example.drinksmachine.uniFeatures.switchFragment

class Finish_Page : Fragment(R.layout.activity_finish_page) {
    var progress = 0
    val handler = Handler(Looper.getMainLooper())
    private lateinit var ObjectBinging: ActivityFinishPageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ObjectBinging = ActivityFinishPageBinding.inflate(inflater, container,false)
        return ObjectBinging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        private lateinit var ImageShow : ImageView
//        private lateinit var TextShow : TextView
//        private lateinit var HomeButtom : Button
//        private lateinit var ProgressBar: ProgressBar
//            ProgressBar = findViewById(R.id.ProgressBar)
//            TextShow = findViewById(R.id.ProgressTextShow)
//            ImageShow = findViewById(R.id.ProgressImageShow)
//            HomeButtom = findViewById(R.id.HomePage_Button)

            ObjectBinging.HomePageButton.visibility = View.INVISIBLE
            startProgress()
            ObjectBinging.HomePageButton.setOnClickListener {
                clearBackStackAndSwitchToMainPage( )
            }
        }
    override  fun onStop( ) {
        super.onStop( )
        handler.removeCallbacksAndMessages( null )
    }

    private fun startProgress( ) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress <= 100) {
                    ObjectBinging.ProgressBar.progress = progress
                    var mess: String = getString(R.string.progressPreparing_text)+progress +"%"
                    ObjectBinging.ProgressTextShow.text = mess
                    progress += 10
                    handler.postDelayed(this, 2_00L) // 每秒更新
                }
                else {
                    ObjectBinging.HomePageButton.visibility = View.VISIBLE
                    ObjectBinging.ProgressTextShow.text = getString(R.string.progressFinish_text)
                    ObjectBinging.ProgressImageShow.setImageResource(R.mipmap.finish)
                    handler.postDelayed({
                        clearBackStackAndSwitchToMainPage( )
                    }, 100_00L)
                }
            }
        }, 15_00L)
    }
    private fun clearBackStackAndSwitchToMainPage() {
        // 清除所有在返回堆疊中的 Fragment
        parentFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // 切換到主頁
        switchFragment(MainPage())
    }
}

