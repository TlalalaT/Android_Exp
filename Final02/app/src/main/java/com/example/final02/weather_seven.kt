package com.example.final02

data class weather_seven(val code: String,
                     val updateTime: String,
                     val fxLink: String,
                     val daily: List<DailyBean>,
                     val refer: ReferBean) {

    data class DailyBean(val fxDate: String,
                         val sunrise: String,
                         val sunset: String,
                         val moonrise: String,
                         val moonset: String,
                         val moonPhase: String,
                         val tempMax: String,
                         val tempMin: String,
                         val iconDay: String,
                         val textDay: String,
                         val iconNight: String,
                         val textNight: String,
                         val wind360Day: String,
                         val windDirDay: String,
                         val windScaleDay: String,
                         val windSpeedDay: String,
                         val wind360Night: String,
                         val windDirNight: String,
                         val windScaleNight: String,
                         val windSpeedNight: String,
                         val humidity: String,
                         val precip: String,
                         val pressure: String,
                         val vis: String,
                         val cloud: String,
                         val uvIndex: String)

    data class ReferBean(val sources: List<String>,
                         val license: List<String>)
}
