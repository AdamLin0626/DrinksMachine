package com.example.drinksmachine.adapterLogic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.drinksmachine.R

data class TextModel(val name: String, val description: String)

class settingListAdapter(
    private var items: MutableList<TextModel>
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var clickCount = 0

    fun setItemsToPretendInfinity(items: MutableList<TextModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.setting_view_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.settingName.text = item.name
        holder.settingShow.text = item.description

        holder.card.setOnClickListener {
            if (item.name == "版本"){
                clickCount++
                if (clickCount == 10) {
                    // 故意模擬崩潰
                    throw RuntimeException("Crashed intentionally after 10 clicks")
                }
            }
        }
    }
}
class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val card: CardView = view as CardView
    val settingName: TextView = view.findViewById(R.id.settingName)
    val settingShow: TextView = view.findViewById(R.id.settingShow)
    val RandomButton: TextView = view.findViewById(R.id.RandomButton)
}
