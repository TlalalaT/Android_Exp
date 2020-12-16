package com.example.final02

import com.example.weathertest.air
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Location {
    @GET("lookup?")
    fun getMessageByGet(@Query("key")key:String, @Query("location")location:String): Call<location_s>
}