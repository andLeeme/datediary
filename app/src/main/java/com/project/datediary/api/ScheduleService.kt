package com.project.datediary.api

import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.model.SignUpRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface ScheduleService {
    @Headers("Content-Type: application/json")
    @POST("/api/main2")
    fun addUserByEnqueue2(@Body ScheduleInfo: ScheduleRequestBody): Call<ArrayList<ScheduleResponseBody>>
}