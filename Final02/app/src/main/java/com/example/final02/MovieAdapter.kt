package com.example.final02

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final02.test.imageDownloader

class MovieAdapter(val movieList: List<movie_show>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val movieImage : ImageView = view.findViewById(R.id.movieImage)
        val movieName : TextView = view.findViewById(R.id.movieName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            //val movies = movieList[holder.adapterPosition]
            val movies = movieList[holder.adapterPosition]
            Log.d("TTTTT",movies.id)
            MovieContentActivity.actionStart(parent.context, movies.id,movies.name,movies.imageUrl)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        imageDownloader.showImage(movie.imageUrl, holder.movieImage)
        holder.movieName.setText(movie.name)
    }

    override fun getItemCount() = movieList.size
}