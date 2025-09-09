package com.example.drinksmachine

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.drinksmachine.databinding.ActivityMainPageBinding
import com.example.drinksmachine.uniFeatures.DialogUtils
import com.example.drinksmachine.uniFeatures.switchFragment
import kotlin.concurrent.thread

class MainPage : Fragment(R.layout.activity_main_page) {

    private lateinit var ObjectBinging: ActivityMainPageBinding
    private var  clickCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ObjectBinging = ActivityMainPageBinding.inflate(inflater, container, false)
        return ObjectBinging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * SettingPage換成要測的當首頁
         */
//        startActivity(Intent(requireContext(), DiyPage::class.java))
//        finish()

        //頁面轉跳區
        val buttonListener = View.OnClickListener { view ->
            when (view) {
                ObjectBinging.logoIcon -> {
                    clickCount++
                    if (clickCount == 10){
                        clickCount = 0

                        DialogUtils.showPasswordDialog(this,
                            onConfirm = {password ->
                                if (password =="theEnd"){
                                    Toast.makeText(requireContext(), "app Crashing", Toast.LENGTH_SHORT).show()
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        // 測試用：強制 Crash
                                        throw RuntimeException("App Crash 測試")
                                    }, 1000)
                                }else{
                                    Toast.makeText(requireContext(), "密碼錯誤！", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onCancel = {
                                Log.v("secretKey", "使用者取消輸入")
                            }
                        )
                    }
                }

                ObjectBinging.DiyButton -> switchFragment(DiyPage())
                ObjectBinging.RandomButton
                     -> Toast.makeText(requireContext(), getString(R.string.not_done), Toast.LENGTH_LONG).show()
                ObjectBinging.HistoryButton -> switchFragment(History_Page())
                ObjectBinging.LoginButton -> startActivity(Intent(requireContext(), Login_Page::class.java))
                ObjectBinging.SignButton -> startActivity(Intent(requireContext(), SignUp_Page::class.java))
                ObjectBinging.SettingButton -> switchFragment(SettingPage())
                ObjectBinging.InfoButton -> switchFragment(Info_Page())
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


    }

}