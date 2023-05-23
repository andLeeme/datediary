package com.project.datediary.api

import com.project.datediary.model.SignUpRequestBody
import com.project.datediary.model.TitleRequestBody
import com.project.datediary.model.TitleResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList
import java.util.HashMap

interface TitleService {
    @Headers("Content-Type: application/json")
    @POST("/api/MonthlyCalendar")
    fun addUserByEnqueue(@Body titleContents: TitleRequestBody): Call<ArrayList<TitleResponseBody>>
}