package com.project.datediary.api

import com.project.datediary.model.UpLoad
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ImageUploadService {
    @Multipart
    @POST("/api/image2")
    fun uploadImage(
        @Part file: MultipartBody.Part, @Part("data") data: HashMap<String, String>): Call<ArrayList<UpLoad>>
}