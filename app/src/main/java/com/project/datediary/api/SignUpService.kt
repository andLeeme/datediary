package com.project.datediary.api

import com.project.datediary.model.SignUpRequestBody
import com.project.datediary.model.SignUpResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList
import java.util.HashMap

interface SignUpService {

//    @Headers("Content-Type: application/json")
//    @POST("/api/postTest")
//    fun addUserByEnqueue(@Body userInfo: SignUpRequestBody): Call<SignUpResponseBody> // Call 은 흐름처리 기능을 제공해줌

    @Headers("Content-Type: application/json")
    @POST("/api/postTest")
    fun addUserByEnqueue(@Body userInfo: SignUpRequestBody): Call<ArrayList<HashMap<String,Object>>>
}

