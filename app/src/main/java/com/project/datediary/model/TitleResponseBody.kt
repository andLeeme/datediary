package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class TitleResponseBody(

    @SerializedName("coupleIndex")
    val coupleIndex: String?,
    @SerializedName("scheduleIndex")
    val scheduleIndex: String?,
    @SerializedName("startYear")
    val startYear: String?,
    @SerializedName("startMonth")
    val startMonth: String?,
    @SerializedName("startDay")
    val startDay: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("endYear")
    val endYear: String?,
    @SerializedName("endMonth")
    val endMonth: String?,
    @SerializedName("endDay")
    val endDay: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("allDayCheck")
    val allDayCheck: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("placeCode")
    val placeCode: String?,
    @SerializedName("missionCode")
    val missionCode: String?,

)

