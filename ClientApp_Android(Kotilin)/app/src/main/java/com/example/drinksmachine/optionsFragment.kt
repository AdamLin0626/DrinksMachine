package com.example.drinksmachine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONObject

class optionsFragment : Fragment( ) {
    private lateinit var dbHelper: OptionDBHelper
//    val db = dbHelper.openDatabase()

    private lateinit var optionType: String

    //    val cursor = db.rawQuery("SELECT * FROM OPTION", null)
    private val selectionMap = mutableMapOf<String, ( ) -> JSONObject?>( )

    companion object {
        // 用於建立此 Fragment 時傳入類型參數（例如 "Drink"）
        fun newInstance(type: String): optionsFragment {
            val fragment = optionsFragment()
            val args = Bundle()
            args.putString("optionType", type) // 傳遞資料類型
            fragment.arguments = args
            return fragment
        }
    }

    // 當 Fragment 創建時初始化資料
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        optionType = arguments?.getString("optionType") ?: ""
        dbHelper = OptionDBHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.option_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val containerLayout = view.findViewById<GridLayout>(R.id.OptionLayoutBox)
        val inflater = LayoutInflater.from(requireContext())

//        val subTypeList = "Grains"
//        val optionForSubType = dbHelper.getOptionsBySubType(subTypeList)

        val allType: List<String>

        // Use a conditional to check the fragment's optionType
        if (optionType == "DRINK") {
            Log.i("DB_Function", "有Drink tag " )
            allType = dbHelper.getOptionsByMainType("DRINK")
        } else if (optionType == "TOPPING") {
            Log.i("DB_Function", "有Topping tag " )
            allType = dbHelper.getOptionsByMainType("TOPPING")
        } else {
            allType = dbHelper.getOptionsBySubType(optionType)
            Log.i("DB_Function", "try to find $optionType")
        }

        for (name in allType) {
            val cardView = inflater.inflate(R.layout.item_windows, containerLayout, false)
             val nameText = cardView.findViewById<TextView>(R.id.OptionName)
             val timeText = cardView.findViewById<TextView>(R.id.OptionTime)
             val plusButton = cardView.findViewById<ImageButton>(R.id.PlusButton)
             val minusButton = cardView.findViewById<ImageButton>(R.id.MinusButton)
            
            nameText.text =name
            var count = 0
            timeText.text = "$count"
            timeText.tag = "$count"

            plusButton.setOnClickListener {
                // 如果是 Drink，總和超過就不加
                if (optionType == "DRINK") {
                    val total = selectionMap.values.sumOf { getData ->
                        (getData()?.getInt("count") ?: 0)
                    }
                    if (total >= 10) {
                        Toast.makeText(requireContext(), getString(R.string.cannot_over_ten), Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if (count < 10) {
                        count++
                        timeText.text = "$count" + "0%"
                        timeText.tag = count
                    }
                }
                if (optionType != "DRINK") {
                    if (count < 10) {
                        count++
                        timeText.text = "$count"
                        timeText.tag = count
                    }
                }
            }


            minusButton.setOnClickListener {
                if (optionType == "DRINK"){
                    if (count > 0){
                        count--
                        timeText.text = "${count}0%"
                        timeText.tag = count
                    }
                    if (count == 0){
                        timeText.text = "$count"
                        timeText.tag = count
                    }
                }
            }

            // ✅ 用 tag 拿到正確的數量
            selectionMap[name] = {
                val currentCount = (timeText.tag as? Int) ?: 0
                Log.d("CheckCount", "$name -> $currentCount")
                if (currentCount > 0) JSONObject().apply {
                    put("name", name)
                    put("count", currentCount)
                } else null
            }

            containerLayout.addView(cardView)
        }
    }

    // 外部可呼叫此方法來取得目前勾選的項目（轉為 JSONObject 格式）
    fun getSelectedOptions(): List<JSONObject> {
        return selectionMap.mapNotNull { (_, getData) ->
            getData()
        }
    }
}
