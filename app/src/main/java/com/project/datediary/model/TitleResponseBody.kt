package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class TitleResponseBody(

    @SerializedName("startYear")
    val startYear: String?,
    @SerializedName("startMonth")
    val startMonth: String?,
    @SerializedName("startDay")
    val startDay: String?,
    @SerializedName("endYear")
    val endYear: String?,
    @SerializedName("endMonth")
    val endMonth: String?,
    @SerializedName("endDay")
    val endDay: String?,
    @SerializedName("allDayCheck")
    val allDayCheck: String?,
    @SerializedName("title")
    val title: String?,

)

