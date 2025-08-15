package com.example.drinksmachine

import com.example.drinksmachine.uniFeatures.MyDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.drinksmachine.databinding.ActivityDiyPageBinding
import com.example.drinksmachine.databinding.ActivityMainPageBinding
import org.json.JSONArray
import org.json.JSONObject

class DiyPage : Fragment() {

    private lateinit var ObjectBinding: ActivityDiyPageBinding

    private lateinit var fragmentDrink: OptionFragment
    private lateinit var fragmentTopping: OptionFragment
    private var fragment_container_drink: Fragment? = null
    private lateinit var  plaintext_drink: String
    private lateinit var  plaintext_topping: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ObjectBinding = ActivityDiyPageBinding.inflate(inflater, container, false)
        return ObjectBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plaintext_drink = getString(R.string.plaintext_drink)
        plaintext_topping = getString(R.string.plaintext_topping)

        // 初始化兩個 Fragment，分別代表不同選項類別
        fragmentDrink = OptionFragment.newInstance("Drink")
        fragmentTopping = OptionFragment.newInstance("Topping")

        // 將兩個 Fragment 同時加入畫面，但預設只顯示 fragmentA
        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container_drink, fragmentDrink)
            .add(R.id.fragment_container_drink, fragmentTopping)
            .hide(fragmentTopping)
            .commit()

        fragment_container_drink = fragmentDrink

        // A 類型按鈕點擊事件（已在 XML 中定義 ID: btnA）
        ObjectBinding.DrinkFragment.setOnClickListener {
            switchToFragment(fragmentDrink)
        }

        // B 類型按鈕點擊事件（已在 XML 中定義 ID: btnB）
        ObjectBinding.ToppingFragment.setOnClickListener {
            switchToFragment(fragmentTopping)
        }

        // 下一步按鈕，整理選擇成 JSON 並換頁
        ObjectBinding.NextPageButton.setOnClickListener {
            val selectedA = fragmentDrink.getSelectedOptions()
            val selectedB = fragmentTopping.getSelectedOptions()

            val json = JSONObject().apply {
                put("TypeA", JSONArray(selectedA))
                put("TypeB", JSONArray(selectedB))
            }

            Log.i("NtPage", json.toString())

            // 將 JSONObject 轉成 name -> count 的 Map
            val drinkCountMap = mutableMapOf<String, Int>()
            for (item in selectedA) {
                val name = item.getString("name")
                val count = item.getInt("count")
                drinkCountMap[name] = count
            }

            val toppingCountMap = mutableMapOf<String, Int>()
            for (item in selectedB) {
                val name = item.getString("name")
                val count = item.getInt("count")
                toppingCountMap[name] = count
            }

            // 建立對齊排版文字（使用固定寬度格式）
            val dialogMessage = buildString {
                append("【"+ plaintext_drink +"】\n" )
                if (drinkCountMap.isEmpty()) {
                    append( getString(R.string.notSelected) + "\n")
                } else {
                    drinkCountMap.forEach { (name, count) ->
                        val percentCount = "$count" + "0％"
                        append(String.format("%-18s  %4s\n", name, percentCount))
                    }
                }

                append("\n【"+ plaintext_topping +"】\n" )
                if (toppingCountMap.isEmpty()) {
                    append( getString(R.string.notSelected) + "\n")
                } else {
                    toppingCountMap.forEach { (name, count) ->
                        append(String.format("%-19s  x%3d\n", name, count))
                    }
                }
            }

            if (drinkCountMap.isEmpty()) {
                // 用 TextView 顯示對話框，指定等寬字體
                val messageAboutError = TextView(requireContext()).apply {
                    text = getString(R.string.drink_is_empty)
                    typeface = Typeface.MONOSPACE  // ✅ 等寬字體
                    setPadding(30, 50, 30, 40)
                    textSize = 16f
                }

                val myDialog = MyDialog(requireContext())
                    .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.sub_color))
                    .setTitle(getString(R.string.plaintext_error))
                    .setMidButtonVisible(false)
                    .setRightButtonVisible(false)
                    .setView(messageAboutError)
                myDialog.setButtonL(getString(R.string.reselect), Color.BLACK) {
                    myDialog.dismiss()
                }
                myDialog.show()

            }
            else{
                // 用 TextView 顯示對話框，指定等寬字體
                val messageView = TextView(requireContext()).apply {
                    text = dialogMessage
                    typeface = Typeface.MONOSPACE  // ✅ 等寬字體
                    setPadding(30, 50, 30, 40)
                    textSize = 16f
                }

                val myDialog = MyDialog(requireContext())
                    .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.sub_color))
                    .setTitle(getString(R.string.confirm_choice))
                    .setView(messageView)
                    .setMidButtonVisible(false)
                myDialog.setButtonL(getString(R.string.reselect), Color.RED) {
                    myDialog.dismiss()
                }
                myDialog.setButtonR(getString(R.string.confirm_button),Color.BLACK){
                    myDialog.dismiss()
                    startActivity(Intent(requireContext() ,Finish_Page::class.java))
                }
                myDialog.show()
            }
        }
    }

    // 切換 Fragment（使用 hide/show 可保留狀態，不重建畫面）
    private fun switchToFragment(target: Fragment) {
        if (fragment_container_drink != target) {
            childFragmentManager.beginTransaction()
                .hide(fragment_container_drink!!)
                .show(target)
                .commit()
            fragment_container_drink = target
        }
    }
}
