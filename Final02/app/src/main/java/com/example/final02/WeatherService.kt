package com.example.final02

import android.telephony.cdma.CdmaCellLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("now?")
    fun getMessageByGet(@Query("key")key:String, @Query("location")location:String): Call<weather_now>
}