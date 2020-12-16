package com.example.final02

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertest.AirNow
import com.example.weathertest.air
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RequestNow("101010100")
        RequestHour("101010100")
        RequestSeven("101010100")
        Search1.setOnClickListener {
            getLocation()
        }
        //imageView.setImageResource(R.drawable.a150)
    }

    fun getLocation(){
        val search = searchInput1.getText().toString()
        Thread {
            val retrofit = Retrofit.Builder().baseUrl("https://geoapi.qweather.com/v2/city/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            Log.i("net", "succe")
            val location = retrofit.create(Location::class.java)
            Log.i("net", "succe")
            location.getMessageByGet("761d26e1d71e48f285f3fefb689b61a4", search)
                .enqueue(object : Callback<location_s> {
                    override fun onResponse(
                        call: Call<location_s>,
                        response: Response<location_s>
                    ) {
                        Log.i("Retrofit", response.toString())
                        val msg = response.body()
                        if (msg != null) {
                            val weatherHourList= ArrayList<location_s>()
                            val loca = msg.location[0].id
                            getActivity()?.runOnUiThread {
                                //responseText.text = msg.toString()
                                RequestNow(loca)
                                RequestHour(loca)
                                RequestSeven(loca)
                            }
                        }
                    }

                    override fun onFailure(call: Call<location_s>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }.start()
    }

    fun RequestNow(location:String){
        Thread {
            //获取当天天气基本情况
            val retrofit = Retrofit.Builder().baseUrl("https://devapi.qweather.com/v7/weather/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            Log.i("net", "succe")
            val weatherService = retrofit.create(WeatherService::class.java)
            Log.i("net", "succe")
            val retrofit2 = Retrofit.Builder().baseUrl("https://geoapi.qweather.com/v2/city/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            Log.i("net", "succe")
            val location2 = retrofit2.create(Location::class.java)
            Log.i("net", "succe")
            location2.getMessageByGet("761d26e1d71e48f285f3fefb689b61a4", location)
                .enqueue(object : Callback<location_s> {
                    override fun onResponse(
                        call: Call<location_s>,
                        response: Response<location_s>
                    ) {
                        Log.i("Retrofit", response.toString())
                        val msg = response.body()
                        if (msg != null) {
                            val weatherHourList= ArrayList<location_s>()
                            val loca = msg.location[0].name
                            getActivity()?.runOnUiThread {
                                //responseText.text = msg.toString()
                                location1.setText(loca)
                            }
                        }
                    }
                    override fun onFailure(call: Call<location_s>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            weatherService.getMessageByGet("761d26e1d71e48f285f3fefb689b61a4", location)
                .enqueue(object : Callback<weather_now> {
                    override fun onResponse(
                        call: Call<weather_now>,
                        response: Response<weather_now>
                    ) {

                        Log.i("Retrofit", response.toString())
                        val msg = response.body()
                        if (msg != null) {
                            getActivity()?.runOnUiThread {
                                refreshTime.text = msg.now.obsTime
                                nowtext.text = msg.now.text
                                nowtemp.text = msg.now.temp + "℃"
                                var icon = msg.now.icon.toInt()
                                imageView.getDrawable().level = icon
                                nowhumidity.text = msg.now.humidity+"%"
                                nowwindDir.text = msg.now.windDir
                                nowwindScale.text = msg.now.windScale+"级"

                            }
                        }
                    }

                    override fun onFailure(call: Call<weather_now>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

            //获取当天空气质量
            val retrofit1 = Retrofit.Builder().baseUrl("https://devapi.qweather.com/v7/air/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            Log.i("net", "succe")
            val airNow = retrofit1.create(AirNow::class.java)
            Log.i("net", "succe")
            airNow.getMessageByGet("761d26e1d71e48f285f3fefb689b61a4", location)
                .enqueue(object : Callback<air> {
                    override fun onResponse(
                        call: Call<air>,
                        response: Response<air>
                    ) {

                        Log.i("Retrofit", response.toString())
                        val msg = response.body()
                        if (msg != null) {
                            getActivity()?.runOnUiThread {
                                nowaqi.text = msg.now.aqi
                                nowcategory.text = msg.now.category
                            }
                        }
                    }

                    override fun onFailure(call: Call<air>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }.start()
    }

    fun RequestHour(location:String){
        //获取逐小时
        Thread {
            val retrofit = Retrofit.Builder().baseUrl("https://devapi.qweather.com/v7/weather/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            Log.i("net", "succe")
            val weatherHour = retrofit.create(WeatherHour::class.java)
            Log.i("net", "succe")
            weatherHour.getMessageByGet("761d26e1d71e48f285f3fefb689b61a4", location)
                .enqueue(object : Callback<weather_per_hour> {
                    override fun onResponse(
                        call: Call<weather_per_hour>,
                        response: Response<weather_per_hour>
                    ) {
                        Log.i("Retrofit", response.toString())
                        val msg = response.body()
                        if (msg != null) {
                            val weatherHourList= ArrayList<weather_hour>()
                            for(i in(0..23)){
                                var weatherh=
                                    weather_hour(msg.hourly[i].fxTime, msg.hourly[i].icon, msg.hourly[i].temp)
                                weatherHourList.add(weatherh)
                            }
                            getActivity()?.runOnUiThread {
                                //responseText.text = msg.toString()
                                val horizontal = LinearLayoutManager(activity)
                                horizontal.orientation= LinearLayoutManager.HORIZONTAL
                                weatherhour.layoutManager = horizontal
                                weatherhour.adapter = WeatherHourAdapter(weatherHourList)
                                (weatherhour.adapter as WeatherHourAdapter).notifyDataSetChanged()
                            }
                        }
                    }

                    override fun onFailure(call: Call<weather_per_hour>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }.start()
    }

    fun RequestSeven(location:String){
        Thread {
            val retrofit = Retrofit.Builder().baseUrl("https://devapi.qweather.com/v7/weather/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            Log.i("net", "succe")
            val weatherSeven = retrofit.create(WeatherSeven::class.java)
            Log.i("net", "succe")
            weatherSeven.getMessageByGet("761d26e1d71e48f285f3fefb689b61a4", location)
                .enqueue(object : Callback<weather_seven> {
                    override fun onResponse(
                        call: Call<weather_seven>,
                        response: Response<weather_seven>
                    ) {
                        Log.i("Retrofit", response.toString())
                        val msg = response.body()
                        if (msg != null) {
                            val weatherSevenList= ArrayList<weather_se>()
                            for(i in(0..6)){
                                var weathers= weather_se(msg.daily[i].fxDate, msg.daily[i].textDay, msg.daily[i].iconDay, msg.daily[i].tempMin+"℃ - "+msg.daily[i].tempMax+"℃")
                                weatherSevenList.add(weathers)
                            }
                            getActivity()?.runOnUiThread {
                                //responseText.text = msg.toString()
                                val vertical = LinearLayoutManager(activity)
                                vertical.orientation= LinearLayoutManager.VERTICAL
                                weatherseven.layoutManager = vertical
                                weatherseven.adapter = WeatherSevenAdapter(weatherSevenList)
                                (weatherseven.adapter as WeatherSevenAdapter).notifyDataSetChanged()
                            }
                        }
                    }

                    override fun onFailure(call: Call<weather_seven>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }.start()
    }
}