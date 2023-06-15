package com.project.datediary.api

import com.project.datediary.model.ScheduleEditRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ScheduleEditService {
    @Headers("Content-Type: application/json")
    @POST("/api/scheduleEdit")
    fun addUserByEnqueue2(@Body ScheduleEdit: ScheduleEditRequestBody): Call<Int>
}