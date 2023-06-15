package com.project.datediary.api

import com.project.datediary.model.Static2ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface Static2Service {
    @Headers("Content-Type: application/json")
    @POST("/api/static2")
    fun addUserByEnqueue(@Body coupleIndex : String?): Call<ArrayList<Static2ResponseBody>>
}