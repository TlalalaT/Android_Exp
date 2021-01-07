package com.example.final02

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.final02.test.imageDownloader
import com.google.gson.Gson

import kotlinx.android.synthetic.main.movie_content.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MovieContentActivity : AppCompatActivity() {

    companion object{
        fun actionStart(context: Context, id:String, name:String, pic:String){
            val intent = Intent(context, MovieContentActivity::class.java).apply {
                putExtra("movie_id", id)
                putExtra("movie_name", name)
                putExtra("movie_pic", pic)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_content)
        /*
        //跳转传过来的数据
        val content = intent.getStringExtra("movie_name")
        //加载url
        web_view.webChromeClient = WebChromeClient()
        web_view.webViewClient = WebViewClient()
        web_view.settings.javaScriptEnabled = true
        web_view.settings.defaultTextEncodingName = "utf-8"
        if (content != null) {
            web_view.loadUrl("https://www.baidu.com/s?ie=UTF-8&wd="+content)
        }

         */
        val movieId = intent.getStringExtra("movie_id")
        val movieName = intent.getStringExtra("movie_name")
        val moviePic = intent.getStringExtra("movie_pic")
        if (movieId != null) {
            if (movieName != null) {
                if (moviePic != null) {
                    getMovieContent(movieId,movieName,moviePic)
                }
            }
        }
    }

    fun getMovieContent(id: String, name: String, pic: String){
        thread {
            var connection: HttpURLConnection?=null
            try {
                val response = StringBuilder()
                val url_str = "https://v.juhe.cn/movie/query?key="+ "da0e99dd235cd6e11b57a678f5bb7798" + "&movieid=" + id
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
                val jsonStr = obj.getString("result")
                Log.d("moviecon",jsonStr)
                val gson = Gson()
                var movieCon = gson.fromJson(jsonStr,MovieContent::class.java)
                movieCon.title = name
                movieCon.poster = pic
                //val newsTest = gson.fromJson(jsonTest,News::class.java)
                showUIchange(movieCon)

            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }
    private fun showUIchange(movieCon: MovieContent){
        runOnUiThread {
            //imageDownloader.showImage(datas[i].thumbnail_pic_s,viewHolder3.img1)
            if (movieCon.poster == null){
                return@runOnUiThread
            }
            imageDownloader.showImage(movieCon.poster,movie_poster)
            movie_title.text = movieCon.title
            movie_info.text = "年代：" + movieCon.year + "\n地区：" + movieCon.film_locations + "\n类型：" + movieCon.genres + "\n评分：" + movieCon.rating
            simple_content.text = movieCon.plot_simple
            directors.text = movieCon.directors
            actors.text = movieCon.actors
        }
    }
    fun backClick(view: View){
        //fanhui
    }
}