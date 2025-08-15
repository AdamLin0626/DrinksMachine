package com.example.drinksmachine.uniFeatures

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.drinksmachine.R
import com.example.drinksmachine.databinding.MyDialogBinding

class MyDialog(context: Context) {
    private val binding = MyDialogBinding.inflate(LayoutInflater.from(context))
    private val dialog = Dialog(context).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE) // 移除標題
        window?.setBackgroundDrawableResource(R.drawable.rectangle_button) // 隱藏預設windows
        setContentView(binding.root)
        setCancelable(false)
    }

    // 動態設定背景顏色
    fun setBackgroundColor(color: Int): MyDialog {
        binding.root.setBackgroundColor(color)
        return this
    }

    fun setTitle(title: String): MyDialog {
        binding.dialogTitle.text = title
        return this
    }

//    fun setIndex(index: String): MyDialog {
//        binding.dialogText.text = index
//        return this
//    }

    fun setView(view: View): MyDialog{
        binding.dialogView.removeAllViews()
        binding.dialogView.addView(view)
        return this
    }

    fun setButtonL(text: String, onClick: (input: String) -> Unit): MyDialog {
        binding.btnLeft.text = text
        binding.btnLeft.setOnClickListener {
            onClick("")
        }
        return this
    }

    /**
     * binding.原件.visibility = ...
     * 如果我打true是可見，反之不可見
     */
    fun setLeftButtonVisible(visible: Boolean): MyDialog {
        binding.btnLeft.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * binding.原件.visibility = ...
     * 如果我打true是可見，反之不可見
     */
    fun setMidButtonVisible(visible: Boolean): MyDialog {
        binding.btnMid.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * binding.原件.visibility = ...
     * 如果我打true是可見，反之不可見
     */
    fun setRightButtonVisible(visible: Boolean): MyDialog {
        binding.btnRight.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setButtonM(text: String, onClick: (input: String) -> Unit): MyDialog {
        binding.btnMid.text = text
        binding.btnMid.setOnClickListener {

        }
        return this
    }

    fun setButtonR(text: String, onClick: (input: String) -> Unit): MyDialog {
        binding.btnRight.text = text
        binding.btnRight.setOnClickListener {
        }
        return this
    }

    fun show(): MyDialog {
        dialog.show()
        return this
    }
    fun dismiss(): MyDialog{
        dialog.dismiss()
        return this
    }
}