package com.project.datediary.api

import com.project.datediary.model.ScheduleDeleteRequestBody
import com.project.datediary.model.ScheduleEditRequestBody
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.model.ScheduleShowRequestBody
import com.project.datediary.model.ScheduleShowResponseBody
import com.project.datediary.model.SignUpRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface ScheduleDeleteService {
    @Headers("Content-Type: application/json")
    @POST("/api/scheduleDelete")
    fun addUserByEnqueue2(@Body ScheduleDelete: ScheduleDeleteRequestBody): Call<Int>
}