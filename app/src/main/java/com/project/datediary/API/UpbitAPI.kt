package com.project.datediary.API

import com.project.datediary.Model.Coin
import retrofit2.Call
import retrofit2.http.GET

interface UpbitAPI {
    @GET("api/main2")
    fun getCoinAll(
    ): Call<List<Coin>>
}