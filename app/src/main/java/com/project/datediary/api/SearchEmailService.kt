package com.project.datediary.api

import com.project.datediary.model.ScheduleEditRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SearchEmailService {
    @Headers("Content-Type: application/json")
    @POST("/api/searchEmail")
    fun addUserByEnqueue2(@Body email:String): Call<Int>
}