package com.project.datediary.api

import com.project.datediary.model.ScheduleShowRequestBody
import com.project.datediary.model.ScheduleShowResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface ScheduleShowService {
    @Headers("Content-Type: application/json")
    @POST("/api/scheduleShow")
    fun addUserByEnqueue2(@Body ScheduleShow: ScheduleShowRequestBody): Call<ArrayList<ScheduleShowResponseBody>>
}