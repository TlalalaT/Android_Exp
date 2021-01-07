package com.example.final02

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_news.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class NewsFragment : Fragment() {
    val menus = listOf<String>("头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚")
    val menusEn = mapOf<String, String>(
        "头条" to "top",
        "社会" to "shehui",
        "国内" to "guonei",
        "国际" to "guoji",
        "娱乐" to "yule",
        "体育" to "tiyu",
        "军事" to "junshi",
        "科技" to "keji",
        "财经" to "caijing",
        "时尚" to "shishang"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsTag_layoutManager = LinearLayoutManager(getActivity())
        newsTag_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        newsTagRecyclerView.layoutManager = newsTag_layoutManager
        val newsTag_adapter = NewsTagAdapter()
        newsTagRecyclerView.adapter = newsTag_adapter
        getNews("top")
    }



    inner class NewsTagAdapter: RecyclerView.Adapter<NewsTagAdapter.ViewHolder>() {
        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val newsTag: TextView = view.findViewById(R.id.news_tag)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.news_tag_item,
                parent,
                false
            )
            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener{
                val newsTag = menus[holder.adapterPosition]
                getNews(newsTag)
                Toast.makeText(activity, newsTag, Toast.LENGTH_SHORT).show()

            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val newsTag = menus[position]
            holder.newsTag.text = newsTag.toString()
        }

        override fun getItemCount() = menus.size
    }

    fun getNews(chooseTag: String){
        thread {
            var connection: HttpURLConnection?=null
            try {
                val response = StringBuilder()
                Log.d("MainActivity",chooseTag)
                val url_str = "https://v.juhe.cn/toutiao/index?" + "type="+ menusEn[chooseTag] +"&key=bd46eda313aae88a81a6f45891c915e2"
                Log.d(chooseTag,url_str)
                val url = URL(url_str)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                var json = ""
                reader.use {
                    reader.forEachLine {
                        json = json + it + "\n"
                    }
                }
                val obj = JSONObject(json)
                val obj1 = obj.getJSONObject("result")
                val jsonArray = obj1.getString("data")
                Log.d(chooseTag,jsonArray)
                val gson = Gson()
                val typrOf = object : TypeToken<List<News>>(){}.type
                val newslist = gson.fromJson<List<News>>(jsonArray, typrOf)
                val newslist1 = ArrayList<News>()
                for (i in 0..10){
                    newslist1.add(newslist[i])
                }

                showUIchange(newslist)
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }
    private fun showUIchange(newslist12: List<News>){
        getActivity()?.runOnUiThread {
            Log.d("MainActivity","uichangeAdapter")
            val myAdapter = MyAdapter(context = getActivity() as Context, datas = newslist12)
            list_view.adapter = myAdapter
            list_view.setOnItemClickListener(AdapterView.OnItemClickListener {
                    parent, view, position, id ->
                //Toast.makeText(this,newslist12[position].title,Toast.LENGTH_SHORT).show()
                //Toast.makeText(this,newslist12[position].author_name,Toast.LENGTH_SHORT).show()
                val intent = Intent(getActivity(),NewsContentActivity::class.java).apply {
                    putExtra("news_title",newslist12[position].title)
                    putExtra("news_author_name",newslist12[position].author_name)
                    putExtra("news_date",newslist12[position].date)
                    putExtra("news_url",newslist12[position].url)
                }
                startActivityForResult(intent,1)
            })

        }
    }
}