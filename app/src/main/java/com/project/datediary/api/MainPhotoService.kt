package com.project.datediary.api

import com.bumptech.glide.load.engine.Resource
import com.project.datediary.model.MainPhotoRequestBody
import com.project.datediary.model.ScheduleShowRequestBody
import com.project.datediary.model.ScheduleShowResponseBody
import com.project.datediary.model.SignUpRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.ArrayList

interface MainPhotoService {
    @Headers("Content-Type: application/json")
    @POST("/api/mobile/download.do")
    fun addUserByEnqueue2(@Body mainPhoto: MainPhotoRequestBody): Call<ResponseEntity<ByteArrayResource>>
}