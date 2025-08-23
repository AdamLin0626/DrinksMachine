package com.example.drinksmachine

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.core.content.ContextCompat
import com.example.drinksmachine.databinding.ActivityDiyPageBinding
import com.example.drinksmachine.uniFeatures.MyDialog
import com.example.drinksmachine.uniFeatures.switchFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class DiyPage : Fragment() {

    private lateinit var Binding: ActivityDiyPageBinding
    private lateinit var fragmentDrink: OptionFragment
    private lateinit var fragmentTopping: OptionFragment
    private var fragmentContainer: Fragment? = null
    private lateinit var plaintextDrink: String
    private lateinit var plaintextTopping: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Binding = ActivityDiyPageBinding.inflate(inflater, container, false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plaintextDrink = getString(R.string.plaintext_drink)
        plaintextTopping = getString(R.string.plaintext_topping)

        // 初始化兩個 Fragment
        fragmentDrink = OptionFragment.newInstance("Drink")
        fragmentTopping = OptionFragment.newInstance("Topping")

        // 只加 fragmentDrink，fragmentTopping 等切換時再 add
        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container_drink, fragmentDrink)
            .commit()

        fragmentContainer = fragmentDrink

        Binding.DrinkFragment.setOnClickListener { switchToFragment(fragmentDrink) }
        Binding.ToppingFragment.setOnClickListener { switchToFragment(fragmentTopping) }

        Binding.NextPageButton.setOnClickListener {
            val selectedA = fragmentDrink.getSelectedOptions()
            val selectedB = fragmentTopping.getSelectedOptions()

            // 在背景線程生成 Dialog 文字
            lifecycleScope.launch {
                val dialogMessage = withContext(Dispatchers.Default) {
                    buildDialogMessage(selectedA, selectedB)
                }

                showDialog(dialogMessage, selectedA.isEmpty())
            }
        }
    }

    private fun switchToFragment(target: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        if (target.isAdded.not()) {
            transaction.add(R.id.fragment_container_drink, target)
        }
        fragmentContainer?.let { transaction.hide(it) }
        transaction.show(target).commit()
        fragmentContainer = target
    }

    private fun buildDialogMessage(selectedA: List<JSONObject>, selectedB: List<JSONObject>): String {
        val drinkCountMap = mutableMapOf<String, Int>()
        val toppingCountMap = mutableMapOf<String, Int>()

        for (item in selectedA) {
            val name = item.getString("name")
            val count = item.getInt("count")
            drinkCountMap[name] = count
        }
        for (item in selectedB) {
            val name = item.getString("name")
            val count = item.getInt("count")
            toppingCountMap[name] = count
        }

        return buildString {
            append("【$plaintextDrink】\n")
            if (drinkCountMap.isEmpty()) {
                append(getString(R.string.notSelected)).append("\n")
            } else {
                drinkCountMap.forEach { (name, count) ->
                    val percentCount = "$count" + "0％"
                    append(String.format("%-18s  %4s\n", name, percentCount))
                }
            }

            append("\n【$plaintextTopping】\n")
            if (toppingCountMap.isEmpty()) {
                append(getString(R.string.notSelected)).append("\n")
            } else {
                toppingCountMap.forEach { (name, count) ->
                    append(String.format("%-19s  x%3d\n", name, count))
                }
            }
        }
    }

    private fun showDialog(message: String, isError: Boolean) {
        // ScrollView 包裹 TextView，避免文字過多造成卡頓
        val messageView = ScrollView(requireContext()).apply {
            val textView = TextView(requireContext()).apply {
                text = message
                typeface = Typeface.MONOSPACE
                setPadding(30, 50, 30, 40)
                textSize = 16f
            }
            addView(textView)
        }

        val myDialog = MyDialog(requireContext())
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.sub_color))
            .setTitle(if (isError) getString(R.string.plaintext_error) else getString(R.string.confirm_choice))
            .setView(messageView)
            .setMidButtonVisible(false)

        if (isError) {
            myDialog.setRightButtonVisible(false)
            myDialog.setButtonL(getString(R.string.reselect), Color.BLACK) { myDialog.dismiss() }
        } else {
            myDialog.setButtonL(getString(R.string.reselect), Color.RED) { myDialog.dismiss() }
            myDialog.setButtonR(getString(R.string.confirm_button), Color.BLACK) {
                myDialog.dismiss()
                switchFragment(Finish_Page())
            }
        }

        myDialog.show()
    }
}
