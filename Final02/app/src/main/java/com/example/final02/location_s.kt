package com.example.final02

data class location_s(val code: String,
                     val location: List<LocationBean>,
                     val refer: ReferBean) {

    data class LocationBean(val name: String,
                            val id: String,
                            val lat: String,
                            val lon: String,
                            val adm2: String,
                            val adm1: String,
                            val country: String,
                            val tz: String,
                            val utcOffset: String,
                            val isDst: String,
                            val type: String,
                            val rank: String,
                            val fxLink: String)

    data class ReferBean(val sources: List<String>,
                         val license: List<String>)
}