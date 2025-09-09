package com.example.drinksmachine.uniFeatures

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.drinksmachine.R

object DialogUtils {

    /** 顯示密碼輸入對話框
     */
    fun showPasswordDialog(
        fragment: Fragment,
        onConfirm: (String) -> Unit,
        onCancel: () -> Unit = {}
    ) {
        val inflater = LayoutInflater.from(fragment.requireContext())
        val dialogView = inflater.inflate(R.layout.password_dialog, null)

        val input = dialogView.findViewById<EditText>(R.id.passwordTextView)
        val okButton = dialogView.findViewById<TextView>(R.id.okButton)
        val cancelButton = dialogView.findViewById<TextView>(R.id.cancelButton)

        val dialog = AlertDialog.Builder(fragment.requireContext())
            .setView(dialogView)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        okButton.setOnClickListener {
            onConfirm(input.text.toString())
        }
        cancelButton.setOnClickListener {
            onCancel()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**正常dialog:
     * indexView 請先將文字轉View
     */
    fun showMessageDialog(
        fragment: Fragment,
        dialogTitle: String,
        leftButtonText: String,
        midButtonText: String,
        rightButtonText: String,
        indexView: View,
        onNext: () -> Unit,
        midButtonFunction: () -> Unit,
        onCancel: () -> Unit ={}
    ){
        val inflater = LayoutInflater.from(fragment.requireContext())
        val dialogView = inflater.inflate(R.layout.my_dialog, null)

        dialogView.findViewById<TextView>(R.id.dialogTitle).text = dialogTitle
        val leftButton = dialogView.findViewById<TextView>(R.id.btnLeft)
        val lineOne = dialogView.findViewById<View>(R.id.v1)
        val midButton = dialogView.findViewById<TextView>(R.id.btnMid)
        val lineTwo = dialogView.findViewById<View>(R.id.v2)
        val rightButton = dialogView.findViewById<TextView>(R.id.btnRight)
        dialogView.findViewById<LinearLayout>(R.id.dialogView).addView(indexView)

        if (leftButtonText.isNotEmpty()) {
            leftButton.visibility = View.VISIBLE
            lineOne.visibility =View.VISIBLE
            leftButton.text = leftButtonText
        } else {
            leftButton.visibility = View.GONE
            lineOne.visibility = View.GONE
        }
        if (midButtonText.isNotEmpty()) {
            midButton.visibility = View.VISIBLE
            lineTwo.visibility =View.VISIBLE
            midButton.text = midButtonText
        } else {
            midButton.visibility = View.GONE
            lineTwo.visibility = View.GONE
        }
        if (rightButtonText.isNotEmpty()) {
            rightButton.visibility = View.VISIBLE
            rightButton.text = rightButtonText
        } else {
            rightButton.visibility = View.GONE
        }

        val dialog = AlertDialog.Builder(fragment.requireContext())
            .setView(dialogView)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        leftButton.setOnClickListener{
            onCancel()
            dialog.dismiss()
        }
        midButton.setOnClickListener{
            midButtonFunction()
            dialog.dismiss()
        }
        rightButton.setOnClickListener{
            onNext()
            dialog.dismiss()
        }
        dialog.show()
    }
}


//class MyDialog(context: Context) {
//    private val binding = MyDialogBinding.inflate(LayoutInflater.from(context))
//    private val dialog = Dialog(context).apply {
//        requestWindowFeature(Window.FEATURE_NO_TITLE) // 移除標題
//        window?.setBackgroundDrawableResource(R.drawable.rectangle_button) // 隱藏預設windows
//        setContentView(binding.root)
//        setCancelable(false)
//    }
//
//    // 動態設定背景顏色
//    fun setBackgroundColor(color: Int): MyDialog {
//        binding.root.setBackgroundColor(color)
//        return this
//    }
//
//    fun setTitle(title: String): MyDialog {
//        binding.dialogTitle.text = title
//        return this
//    }
//
////    fun setIndex(index: String): MyDialog {
////        binding.dialogText.text = index
////        return this
////    }
//
//    fun setView(view: View): MyDialog{
//        binding.dialogView.removeAllViews()
//        binding.dialogView.addView(view)
//        return this
//    }
//
//    /**
//     * binding.原件.visibility = ...
//     * 如果我打true是可見，反之不可見
//     */
//    fun setLeftButtonVisible(visible: Boolean): MyDialog {
//        binding.btnLeft.visibility = if (visible) View.VISIBLE else View.GONE
//        binding.v1.visibility = if (visible) View.VISIBLE else View.GONE
//
//        return this
//    }
//    fun setButtonL(text: String, color: Int, onClick: (input: String) -> Unit): MyDialog {
//        binding.btnLeft.text = text
//        binding.btnLeft.setOnClickListener {
//            onClick("")
//        }
//        binding.btnLeft.setTextColor(color)
//        return this
//    }
//
//    /**
//     * binding.原件.visibility = ...
//     * 如果我打true是可見，反之不可見
//     */
//    fun setMidButtonVisible(visible: Boolean): MyDialog {
//        binding.btnMid.visibility = if (visible) View.VISIBLE else View.GONE
//        binding.v2.visibility = if (visible) View.VISIBLE else View.GONE
//        return this
//    }
//    fun setButtonM(text: String, color: Int, onClick: (input: String) -> Unit): MyDialog {
//        binding.btnMid.text = text
//        binding.btnLeft.setOnClickListener {
//            onClick("")
//        }
//        binding.btnMid.setTextColor(color)
//        return this
//    }
//
//    /**
//     * binding.原件.visibility = ...
//     * 如果我打true是可見，反之不可見
//     */
//    fun setRightButtonVisible(visible: Boolean): MyDialog {
//        binding.btnRight.visibility = if (visible) View.VISIBLE else View.GONE
//        return this
//    }
//    fun setButtonR(text: String, color: Int, onClick: (input: String) -> Unit): MyDialog {
//        binding.btnRight.text = text
//        binding.btnRight.setOnClickListener {
//            onClick("")
//        }
//        binding.btnRight.setTextColor(color)
//        return this
//    }
//
//    fun show(): MyDialog {
//        dialog.show()
//        return this
//    }
//    fun dismiss(): MyDialog{
//        dialog.dismiss()
//        return this
//    }
//}
