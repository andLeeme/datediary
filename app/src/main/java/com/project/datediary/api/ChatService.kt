package com.project.datediary.api

import com.project.datediary.model.ChatRequestBody
import com.project.datediary.model.NoticeResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface ChatService {
    @Headers("Content-Type: application/json")
    @POST("/api/notice")
    fun addUserByEnqueue(@Body ChatRequestBody: ChatRequestBody): Call<ArrayList<NoticeResponseBody>>
}