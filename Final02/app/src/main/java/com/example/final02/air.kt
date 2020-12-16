package com.example.weathertest

data class air(val code: String,
                     val updateTime: String,
                     val fxLink: String,
                     val now: NowBean,
                     val station: List<StationBean>,
                     val refer: ReferBean) {

    data class NowBean(val pubTime: String,
                       val aqi: String,
                       val level: String,
                       val category: String,
                       val primary: String,
                       val pm10: String,
                       val pm2p5: String,
                       val no2: String,
                       val so2: String,
                       val co: String,
                       val o3: String)

    data class StationBean(val pubTime: String,
                           val name: String,
                           val id: String,
                           val aqi: String,
                           val level: String,
                           val category: String,
                           val primary: String,
                           val pm10: String,
                           val pm2p5: String,
                           val no2: String,
                           val so2: String,
                           val co: String,
                           val o3: String)

    data class ReferBean(val sources: List<String>,
                         val license: List<String>)
}