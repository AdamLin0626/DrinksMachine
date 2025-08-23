package com.example.drinksmachine.adapterLogic

import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinksmachine.databinding.SettingItemBinding

data class SettingItem(
    val title: String,
    val value: String = "",
    val showArrow: Boolean = false
)

class SettingAdapter(
    private val items: List<SettingItem>,
    private val onClick: (SettingItem) -> Unit
) : RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SettingItem) {
            binding.titleText.text = item.title
            binding.informationText.text = item.value
            if (item.showArrow) binding.arrow.setTextColor(Color.BLACK) else binding.arrow.setTextColor(Color.WHITE)

            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SettingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
