package com.example.final02
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherSeven {
    @GET("7d?")
    fun getMessageByGet(@Query("key")key:String, @Query("location")location:String): Call<weather_seven>
}