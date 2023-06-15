package com.project.datediary.api

import com.project.datediary.model.ScheduleRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ScheduleService {
    @Headers("Content-Type: application/json")
    @POST("/api/scheduleReg")
    fun addUserByEnqueue2(@Body ScheduleInfo: ScheduleRequestBody): Call<Int>
}