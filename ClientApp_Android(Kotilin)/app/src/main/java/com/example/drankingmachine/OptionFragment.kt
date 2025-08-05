package com.example.drankingmachine

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

class OptionFragment : Fragment() {
    private val selectionMap = mutableMapOf<String, () -> JSONObject?>()

    // 接收從 DiyPage 傳入的類型參數（例如 "Drink" 或 "Topping"）
    private lateinit var optionType: String

    // 資料庫輔助工具
    private lateinit var dbHelper: OptionDBHelper


    companion object {
        // 用於建立此 Fragment 時傳入類型參數（例如 "Drink"）
        fun newInstance(type: String): OptionFragment {
            val fragment = OptionFragment()
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
        dbHelper = OptionDBHelper(requireContext()) // 初始化資料庫輔助類
    }

    // 載入畫面 fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.option_fragment, container, false)
    }

    // 畫面建立後，開始從資料庫讀取資料，動態建立 CheckBox
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val containerLayout = view.findViewById<GridLayout>(R.id.OptionLayoutBox)
        val inflater = LayoutInflater.from(requireContext())

        val options = dbHelper.getOptionsByType(optionType)

        for (name in options) {
            val cardView = inflater.inflate(R.layout.item_windows, containerLayout, false)

            val nameText = cardView.findViewById<TextView>(R.id.OptionName)
            val timeText = cardView.findViewById<TextView>(R.id.OptionTime)
            val plusBtn = cardView.findViewById<ImageButton>(R.id.PlusButton)
            val minusBtn = cardView.findViewById<ImageButton>(R.id.MinusButton)

            nameText.text = name
            var count = 0
            timeText.text = "$count"
            timeText.tag = count

            plusBtn.setOnClickListener {
                // 如果是 Drink，總和超過就不加
                if (optionType == "Drink") {
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
                if (optionType != "Drink") {
                    if (count < 10) {
                        count++
                        timeText.text = "$count"
                        timeText.tag = count
                    }
                }
            }


            minusBtn.setOnClickListener {
                if (optionType == "Drink"){
                    if (count > 0){
                        count--
                        timeText.text = "$count" + "0%"
                        timeText.tag = count
                    }
                    if (count == 0){
                        timeText.text = "$count" + "%"
                        timeText.tag = count
                    }
                }
                if (optionType != "Drink"){
                    if (count > 0){
                        count--
                        timeText.text = "$count" + "0%"
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
