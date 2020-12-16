package com.example.final02

data class movie(val reason: String,
                     val result: List<ResultBean>,
                     val error_code: Int) {

    data class ResultBean(val movieId: String,
                          val movieName: String,
                          val pic_url: String)
}