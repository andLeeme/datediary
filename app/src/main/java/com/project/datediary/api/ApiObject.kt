package com.project.datediary.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiObject {
//    private const val BASE_URL = "http://192.168.150.120:8080/"
    private const val BASE_URL = "http://192.168.182.67:8080/"


    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService : UpbitAPI by lazy { getRetrofit.create(UpbitAPI::class.java) }
}