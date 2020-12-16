package com.example.final02

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_movie_content.*

class MovieContentActivity : AppCompatActivity() {

    companion object{
        fun actionStart(context: Context, name:String){
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra("movie_name", name)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_content)
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
    }
}