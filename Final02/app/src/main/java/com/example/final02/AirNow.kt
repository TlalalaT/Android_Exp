package com.example.weathertest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirNow {
    @GET("now?")
    fun getMessageByGet(@Query("key")key:String, @Query("location")location:String): Call<air>
}