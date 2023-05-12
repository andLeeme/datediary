package com.project.datediary.api

import com.project.datediary.model.Coin
import retrofit2.Call
import retrofit2.http.GET

interface UpbitAPI {
    @GET("api/main2")
    fun getCoinAll(
    ): Call<List<Coin>>
}