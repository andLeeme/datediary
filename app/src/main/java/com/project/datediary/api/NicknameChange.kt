package com.project.datediary.api

import com.project.datediary.model.NicknameChangeRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NicknameChange {
    @Headers("Content-Type: application/json")
    @POST("/api/nicknameChange")
    fun addUserByEnqueue2(@Body userInfo: NicknameChangeRequestBody): Call<Int>
}