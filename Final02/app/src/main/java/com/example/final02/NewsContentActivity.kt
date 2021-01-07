package com.example.final02

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_news_content.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import kotlin.concurrent.thread

class NewsContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
        val title = intent.getStringExtra("news_title")
        val author_name = intent.getStringExtra("news_author_name")
        val date = intent.getStringExtra("news_date")
        val url = intent.getStringExtra("news_url")
        newsContentTitle.text = title
        newsContentDateAuthor.text = date + "  " + author_name
        Log.d("content-url",url.toString())
        getContent(url.toString())
    }

    fun getContent(url: String){

        thread {
            var document: Document? = null
            var ncptaglist = ArrayList<newsContentP>()
            Log.d("co-url",url.toString())
            try {
                document = Jsoup.connect(url).get()
                Log.d("content",document.toString())
                val elements : Elements = document.getElementsByClass("J-article-content article-content")

                //J-article-content article-content
                for (element in elements){

                    Log.d("TTTT",element.toString())
                    val eimages = element.getElementsByClass("section img")
                    //section img
                    //var pkongge = 1
                    var tmp = 0
                    for (et in element.getElementsByTag("p")){
                        val contentText = et.text()
                        ncptaglist.add(newsContentP(contentText,newsContentP.TYPE_TEXT))
                        if (tmp < eimages.size){
                            ncptaglist.add(newsContentP(eimages.get(tmp).getElementsByTag("a").attr("data-href"),newsContentP.TYPE_IMAG))
                            tmp++
                        }

                        /*
                        if (contentText == "" && pkongge == 1){
                            ncptaglist.add(newsContentP("https:" + eimages.get(tmp).getElementsByTag("a").attr("data-href"),newsContentP.TYPE_IMAG))
                            tmp++
                            pkongge = 2
                        } else if (contentText == "" && pkongge == 2){
                            pkongge = 1
                        }

                         */
                    }
                    //ncptaglist.add(newsContentP(element.getElementsByTag("p").text(),newsContentP.TYPE_TEXT))
                    //ncptaglist.add(newsContentP("https:" + element.select("div[class=widt_ad] img").get(tmp).attr("data-url"),newsContentP.TYPE_IMAG))
                    /*
                    for (es in element.getElementsByClass("widt_ad")){
                        ncptaglist.add(newsContentP("https:" + es.getElementsByTag("img").attr("data-url"),newsContentP.TYPE_IMAG))
                        //Log.d("getContent",es.getElementsByTag("img").attr("data-url"))
                    }

                     */


                }
                showUIchange2(ncptaglist)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

    fun showUIchange2(ncptaglist12: ArrayList<newsContentP>){
        runOnUiThread {
            val layoutManager = LinearLayoutManager(this)
            newsContentPtagRecyclerView.layoutManager = layoutManager
            val adapter = newsConPtagAdapter(ncptaglist12)
            newsContentPtagRecyclerView.adapter = adapter
        }
    }

    fun backClick(view: View){
        val intent = Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}