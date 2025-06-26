package com.example.drankingmachine

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

//資料類別轉換
data class CardItem(
    val page: String,
    val imageName: String,
    val text: String
)

class DrinkOptionAdapter(
    private val item:List<CardItem>,
    private val context: Context): RecyclerView.Adapter<DrinkOptionAdapter.ViewHolder> {
    val imageName :ImgeView = view
}