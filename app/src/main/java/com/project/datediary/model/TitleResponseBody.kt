package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class TitleResponseBody(

    @SerializedName("start_year")
    val start_year: String?,
    @SerializedName("start_month")
    val start_month: String?,
    @SerializedName("start_day")
    val start_day: String?,
    @SerializedName("end_year")
    val end_year: String?,
    @SerializedName("end_month")
    val end_month: String?,
    @SerializedName("end_day")
    val end_day: String?,
    @SerializedName("allDayCheck")
    val allDayCheck: String?,
    @SerializedName("title")
    val title: String?,

)

