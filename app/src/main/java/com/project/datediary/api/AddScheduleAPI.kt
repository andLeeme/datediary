package com.project.datediary.api

import com.project.datediary.model.addScheduleRequest
import com.project.datediary.model.addScheduleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AddScheduleAPI {


    // Json
    @POST("api/addSchedule") // Call<InitializeResponse> 데이터를 받을 data class
    fun addScheduleRequest(@Body initializeRequest: addScheduleRequest): Call<addScheduleResponse> // InitializeRequest 요청을 보낼 Json Data Class


}
