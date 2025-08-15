package com.example.drinksmachine

import android.content.Intent
import android.graphics.Color
import com.example.drinksmachine.uniFeatures.MyDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.Style
import androidx.core.content.ContextCompat
import com.example.drinksmachine.databinding.ActivitySettingPageBinding

class SettingPage : AppCompatActivity() {

    private lateinit var OptionBinding: ActivitySettingPageBinding

    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OptionBinding =ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(OptionBinding.root)

        val TitleText = ContextCompat.getString(this, R.string.title_setting)
        val BarColor = ContextCompat.getColor(this, R.color.sub_color)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(BarColor))
        supportActionBar?.setTitle(TitleText)

        OptionBinding.textFirmwareNumber.text = "v25.0814.a"
        val buttonListener = View.OnClickListener { view->
            when(view) {
                OptionBinding.boxFirmwareNumber -> {
                    clickCount++
                    if (clickCount == 10) {
                        throw RuntimeException("App Closing!")
                    }
                }
                OptionBinding.boxLanguage -> {
                    val dialog = MyDialog(this)
                        .setTitle("選擇語言啦～幹！")
                        .setBackgroundColor(ContextCompat.getColor(this, R.color.main_color))
                        .setLeftButtonVisible(true)
                        .setMidButtonVisible(false)
                        .setRightButtonVisible(false)
                    dialog.setButtonR(getString(R.string.reselect) ,ContextCompat.getColor(this, R.color.sub_color)){
                        Toast.makeText(this, "按下左",Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                    }
                    dialog.show()
                }
                OptionBinding.boxBrightness ->{
                    val dialog = MyDialog(this)
                        .setTitle("亮度")
                        .setBackgroundColor(ContextCompat.getColor(this, R.color.main_color))
                        .setLeftButtonVisible(true)
                        .setMidButtonVisible(false)
                        .setRightButtonVisible(false)
                    dialog.setButtonR(getString(R.string.reselect),Color.CYAN){
                        Toast.makeText(this, "按下左",Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }
        OptionBinding.boxFirmwareNumber.setOnClickListener(buttonListener)
        OptionBinding.boxLanguage.setOnClickListener(buttonListener)
        OptionBinding.boxLocation.setOnClickListener(buttonListener)
        //        val list = getTextList()
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        val adapter = settingListAdapter(list)
//        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = linearLayoutManager
    }

//    private fun getTextList() = mutableListOf(
//        TextModel("版本", "20250811"),
//        TextModel("11", "20250811"),
//        TextModel("22", "20250811")
//    )
}