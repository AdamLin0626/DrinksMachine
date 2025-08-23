package com.example.drinksmachine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.drinksmachine.databinding.ActivityHistoryPageBinding

class History_Page : Fragment(R.layout.activity_history_page){
    private lateinit var ObjectBinging: ActivityHistoryPageBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View{
        ObjectBinging = ActivityHistoryPageBinding.inflate(inflater, container, false)
        return ObjectBinging.root
    }
}