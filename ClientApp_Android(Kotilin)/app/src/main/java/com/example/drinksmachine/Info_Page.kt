package com.example.drinksmachine

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.drinksmachine.databinding.ActivityInfoPageBinding

class Info_Page: Fragment(R.layout.activity_info_page){
    private lateinit var ObjectBinding: ActivityInfoPageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ObjectBinding =ActivityInfoPageBinding.inflate(inflater,container,false)
        return ObjectBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val inputStream = requireContext().assets.open("Information.txt")
        val content = inputStream.bufferedReader().use { it.readText() }

        ObjectBinding.infoText.apply {
            text = content
            textSize = 20f
            setTextColor(Color.BLACK)
        }
    }
}