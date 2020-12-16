package com.example.final02
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final02.R

class WeatherSevenAdapter (val weathersevenList: List<weather_se>) : RecyclerView.Adapter<WeatherSevenAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.time2)
        val icon1: ImageView = view.findViewById(R.id.icon2)
        val temp1: TextView = view.findViewById(R.id.temp2)
        val text1: TextView = view.findViewById(R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_seven_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weathersevenList[position]
        holder.time.text = weather.time
        holder.icon1.getDrawable().level = weather.icon.toInt()
        holder.temp1.text = weather.temp
        holder.text1.text = weather.text
    }

    override fun getItemCount() = weathersevenList.size
}