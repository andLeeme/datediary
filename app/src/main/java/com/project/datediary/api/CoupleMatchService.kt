package com.project.datediary.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CoupleMatchService {
    @Headers("Content-Type: application/json")
    @POST("/api/matchCouple")
    fun addUserByEnqueue2(@Body data : HashMap<String, String>): Call<Int>
}