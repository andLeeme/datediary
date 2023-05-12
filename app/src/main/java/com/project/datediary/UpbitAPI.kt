package com.project.datediary

import retrofit2.Call
import retrofit2.http.GET

interface UpbitAPI {
    @GET("api/main2")
    fun getCoinAll(
    ): Call<List<Coin>>
}