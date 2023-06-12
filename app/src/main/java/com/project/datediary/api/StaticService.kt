package com.project.datediary.api

import com.project.datediary.model.SignUpRequestBody
import com.project.datediary.model.StaticRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList
import java.util.HashMap

interface StaticService {
    @Headers("Content-Type: application/json")
    @POST("/api/static1")
    fun addUserByEnqueue(@Body static1: StaticRequestBody): Call<HashMap<Integer, String>>
}