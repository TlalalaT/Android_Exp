package com.example.final02

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class newsConPtagViewHolder(view: View) : RecyclerView.ViewHolder(view)

class PtagTextViewHolder(view: View) : newsConPtagViewHolder(view){
    val ptagText : TextView = view.findViewById(R.id.news_con_text)
}

class PtagImagViewHolder(view: View) : newsConPtagViewHolder(view){
    val ptagImag : ImageView = view.findViewById(R.id.news_con_imag)
}