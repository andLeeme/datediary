package com.project.datediary.api

import com.project.datediary.model.SignUpRequestBody
import com.project.datediary.model.Static3RequestBody
import com.project.datediary.model.Static3ResponseBody
import com.project.datediary.model.StaticRequestBody
import com.project.datediary.model.StaticResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList
import java.util.HashMap

interface Static3Service {
    @Headers("Content-Type: application/json")
    @POST("/api/static3")
    fun addUserByEnqueue(@Body static3: Static3RequestBody): Call<ArrayList<Static3ResponseBody>>
}