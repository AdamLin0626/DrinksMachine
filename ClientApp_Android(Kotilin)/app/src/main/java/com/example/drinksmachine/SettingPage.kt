package com.example.drinksmachine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksmachine.adapterLogic.SettingAdapter
import com.example.drinksmachine.adapterLogic.SettingItem
import com.example.drinksmachine.databinding.ActivitySettingPageBinding

class SettingPage : Fragment() {

    private lateinit var OptionBinding: ActivitySettingPageBinding

    private var clickCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        OptionBinding = ActivitySettingPageBinding.inflate(inflater, container ,false)
        return OptionBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings = listOf(
            SettingItem(getString(R.string.title_firmwareNumber), "v25.8.27"),
            SettingItem(getString(R.string.title_bluetooth), "已連線", showArrow = true),
            SettingItem(getString(R.string.title_language), "繁體中文", showArrow = true),
            SettingItem(getString(R.string.title_location), "台灣", showArrow = true),
            SettingItem(getString(R.string.title_brightness), "80%", showArrow = true),
            SettingItem(getString(R.string.title_need_help), "Tel: 0966225065")
        )

        val adapter = SettingAdapter(settings) { item ->
            when(item.title) {
                getString(R.string.title_firmwareNumber) ->Toast.makeText(requireContext(), getString(R.string.title_firmwareNumber),Toast.LENGTH_SHORT).show()
                getString(R.string.title_bluetooth) ->Toast.makeText(requireContext(), getString(R.string.title_bluetooth),Toast.LENGTH_SHORT).show()
                getString(R.string.title_language) -> Toast.makeText(requireContext(), getString(R.string.title_language),Toast.LENGTH_SHORT).show()
                getString(R.string.title_location) -> Toast.makeText(requireContext(), getString(R.string.title_location),Toast.LENGTH_SHORT).show()
                getString(R.string.title_brightness) ->Toast.makeText(requireContext(), getString(R.string.title_brightness),Toast.LENGTH_SHORT).show()
                getString(R.string.title_need_help) ->Toast.makeText(requireContext(), getString(R.string.title_need_help),Toast.LENGTH_SHORT).show()
            }
        }

        OptionBinding.recyclerView.adapter = adapter
        OptionBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}