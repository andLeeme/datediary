package com.project.datediary.api

import com.project.datediary.model.ScheduleDeleteRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ScheduleDeleteService {
    @Headers("Content-Type: application/json")
    @POST("/api/scheduleDelete")
    fun addUserByEnqueue2(@Body ScheduleDelete: ScheduleDeleteRequestBody): Call<Int>

}