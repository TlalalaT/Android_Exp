package com.example.final02

data class weather_per_hour(val code: String,
                     val updateTime: String,
                     val fxLink: String,
                     val hourly: List<HourlyBean>,
                     val refer: ReferBean) {

    data class HourlyBean(val fxTime: String,
                          val temp: String,
                          val icon: String,
                          val text: String,
                          val wind360: String,
                          val windDir: String,
                          val windScale: String,
                          val windSpeed: String,
                          val humidity: String,
                          val pop: String,
                          val precip: String,
                          val pressure: String,
                          val cloud: String,
                          val dew: String)

    data class ReferBean(val sources: List<String>,
                         val license: List<String>)
}