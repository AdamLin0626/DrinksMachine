package com.example.drankingmachine

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.drankingmachine.databinding.ActivityDiyPageBinding
import com.example.drankingmachine.databinding.MyDialogBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.zip.Inflater

class DiyPage : AppCompatActivity() {

    private lateinit var objectBinding: ActivityDiyPageBinding
    private lateinit var dialogBinding: MyDialogBinding
    private lateinit var fragmentDrink: OptionFragment
    private lateinit var fragmentTopping: OptionFragment
    private var fragment_container_drink: Fragment? = null
    private lateinit var  plaintext_drink: String
    private lateinit var  plaintext_topping: String
    private lateinit var dialog: Dialog
    private lateinit var viewDalog: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        objectBinding = ActivityDiyPageBinding.inflate(layoutInflater)
        setContentView(objectBinding.root)

        supportActionBar?.hide()
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_diy_page)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        plaintext_drink = getString(R.string.plaintext_drink)
        plaintext_topping = getString(R.string.plaintext_topping)

        // 初始化兩個 Fragment，分別代表不同選項類別
        fragmentDrink = OptionFragment.newInstance("Drink")
        fragmentTopping = OptionFragment.newInstance("Topping")

        // 將兩個 Fragment 同時加入畫面，但預設只顯示 fragmentA
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_drink, fragmentDrink)
            .add(R.id.fragment_container_drink, fragmentTopping)
            .hide(fragmentTopping)
            .commit()

        fragment_container_drink = fragmentDrink

        // A 類型按鈕點擊事件（已在 XML 中定義 ID: btnA）
        objectBinding.DrinkFragment.setOnClickListener {
            switchToFragment(fragmentDrink)
        }

        // B 類型按鈕點擊事件（已在 XML 中定義 ID: btnB）
        objectBinding.ToppingFragment.setOnClickListener {
            switchToFragment(fragmentTopping)
        }

        // 下一步按鈕，整理選擇成 JSON 並換頁
        objectBinding.NextPageButton.setOnClickListener {
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
                    append( getString(R.string.not_selected) + "\n")
                } else {
                    drinkCountMap.forEach { (name, count) ->
                        val percentCount = "$count" + "0％"
                        append(String.format("%-18s  %4s\n", name, percentCount))
                    }
                }

                append("\n【"+ plaintext_topping +"】\n" )
                if (toppingCountMap.isEmpty()) {
                    append( getString(R.string.not_selected) + "\n")
                } else {
                    toppingCountMap.forEach { (name, count) ->
                        append(String.format("%-19s  x%3d\n", name, count))
                    }
                }
            }

            // 用 TextView 顯示對話框，指定等寬字體
            val messageView = TextView(this).apply {
                text = dialogMessage
                typeface = Typeface.MONOSPACE  // ✅ 等寬字體
                setPadding(60, 50, 60, 30)
                textSize = 16f
            }
            dialog = Dialog(this)
            dialogBinding = MyDialogBinding.inflate(layoutInflater)
            dialogBinding.dialogTitle
//            if (drinkCountMap.isEmpty()) {
//                AlertDialog.Builder(this)
//                    .setTitle(getString(R.string.plaintext_error))
//                    .setMessage("\n\n" + getString(R.string.drink_is_empty) + "\n\n")
//                    .setNegativeButton(getString(R.string.confirm_button), null)
//                    .show()
//            }
            if (drinkCountMap.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.plaintext_error))
                    .setMessage("\n\n" + getString(R.string.drink_is_empty) + "\n\n")
                    .setNegativeButton(getString(R.string.confirm_button), null)
                    .show()
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.confirm_choice))
                    .setView(messageView)
                    .setPositiveButton(getString(R.string.confirm_button)) { _, _ ->
                        startActivity(Intent(this, Finish_Page::class.java))
                    }
                    .setNegativeButton(getString(R.string.reselect), null)
                    .show()
            }
        }
    }

    // 切換 Fragment（使用 hide/show 可保留狀態，不重建畫面）
    private fun switchToFragment(target: Fragment) {
        if (fragment_container_drink != target) {
            supportFragmentManager.beginTransaction()
                .hide(fragment_container_drink!!)
                .show(target)
                .commit()
            fragment_container_drink = target
        }
    }

}
