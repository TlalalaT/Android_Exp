package com.example.final02

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final02.test.imageDownloader

class newsConPtagAdapter(val newsConPtagList: List<newsContentP>) :  RecyclerView.Adapter<newsConPtagViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        val ncptag = newsConPtagList[position]
        return ncptag.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == newsContentP.TYPE_TEXT){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_con_ptag_text,parent,false)
            PtagTextViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_con_ptag_imag,parent,false)
            PtagImagViewHolder(view)
        }

    override fun onBindViewHolder(holder: newsConPtagViewHolder, position: Int) {
        val ncptag = newsConPtagList[position]
        when (holder){
            is PtagTextViewHolder -> {
                holder.ptagText.text = ncptag.content
            }
            is PtagImagViewHolder -> {
                imageDownloader.showImage(ncptag.content,holder.ptagImag)
            }
        }
    }

    override fun getItemCount() = newsConPtagList.size
}