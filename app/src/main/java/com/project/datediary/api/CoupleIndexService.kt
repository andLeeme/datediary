package com.project.datediary.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CoupleIndexService {
    @Headers("Content-Type: application/json")
    @POST("/api/coupleIndex")
    fun addUserByEnqueue2(@Body email : String?): Call<HashMap<String,String>>
}