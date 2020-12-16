package com.example.final02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovie()
    }

    fun getMovie() {
        Thread {
            val retrofit = Retrofit.Builder().baseUrl("https://v.juhe.cn/movie/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            val Movie = retrofit.create(MovieGet::class.java)
            Movie.getMessageByGet("fb28fa3a11d9cc66428a137deb7e9fb3", 2)
                .enqueue(object : Callback<movie> {
                    override fun onResponse(
                        call: Call<movie>,
                        response: Response<movie>
                    ) {
                        val msg = response.body()
                        if (msg != null) {
                            val movieList = ArrayList<movie_show>()
                            for (i in (0..(msg.result.size-1))) {
                                val movieshow = movie_show(msg.result[i].movieName, msg.result[i].pic_url)
                                movieList.add(movieshow)
                            }
                            getActivity()?.runOnUiThread {
                                //responseText.text = msg.toString()
                                val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                                recyclerViewMovie.layoutManager = layoutManager
                                val adapter = MovieAdapter(movieList)
                                recyclerViewMovie.adapter = adapter
                            }
                        }
                    }

                    override fun onFailure(call: Call<movie>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }.start()
    }

}