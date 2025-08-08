package com.example.drankingmachine

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drankingmachine.adapterLogic.TextModel
import com.example.drankingmachine.adapterLogic.settingListAdapter

class Setting_Page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val TitleText = ContextCompat.getString(this, R.string.title_setting)
        val BarColor = ContextCompat.getColor(this, R.color.sub_color)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(BarColor))
        supportActionBar?.setTitle(TitleText)

        setContentView(R.layout.activity_setting_page)

        val list = getTextList()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = settingListAdapter(list)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun getTextList() = mutableListOf(
        TextModel("版本", "20250811"),
        TextModel("11", "20250811"),
        TextModel("22", "20250811")
    )
}