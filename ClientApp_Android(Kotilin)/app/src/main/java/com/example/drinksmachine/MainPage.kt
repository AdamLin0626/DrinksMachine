package com.example.drinksmachine

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.drinksmachine.databinding.ActivityMainPageBinding
import com.example.drinksmachine.uniFeatures.MyDialog
import com.example.drinksmachine.uniFeatures.switchFragment

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
                        val dialog = MyDialog(requireContext())
                            .setTitle("關閉畫面中～ 密碼?")
                            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                            .setMidButtonVisible(false)
                        val editText = EditText(requireContext()).apply {
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
                            if( pass == "TheEnd") throw RuntimeException("App Crash") else Toast.makeText(requireContext(), "Password ❌",Toast.LENGTH_SHORT).show()
                        }
                        dialog.setButtonL(getString(R.string.exit), Color.BLACK){
                            clickCount = 0
                            dialog.dismiss()
                        }
                        dialog.show()
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