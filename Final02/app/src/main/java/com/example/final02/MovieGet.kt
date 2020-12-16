package com.example.final02

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieGet {
    @GET("movies.today?")
    fun getMessageByGet(@Query("key")key:String, @Query("cityid")cityid:Int): Call<movie>
}