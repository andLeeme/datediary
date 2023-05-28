package com.project.datediary.api

import com.project.datediary.model.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageUploadService {
    @Multipart
    @POST("api/image")
    fun uploadImage(@Part file: MultipartBody.Part): Call<ApiResponse>
}