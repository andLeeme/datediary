package com.project.datediary.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface AddCoupleEmailService {
    @Headers("Content-Type: application/json")
    @POST("/api/coupleEmail")
    fun addUserByEnqueue2(@Body email : HashMap<String, String>): Call<Int>
}