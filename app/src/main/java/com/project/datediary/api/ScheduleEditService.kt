package com.project.datediary.api

import com.project.datediary.model.ScheduleEditRequestBody
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.model.SignUpRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface ScheduleEditService {
    @Headers("Content-Type: application/json")
    @POST("/api/scheduleEdit")
    fun addUserByEnqueue2(@Body ScheduleEdit: ScheduleEditRequestBody): Call<Int>
}