package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class ScheduleShowResponseBody(
    @SerializedName("couple_index")
    val couple_index: String?,
    @SerializedName("schedule_index")
    val schedule_index: String?,
    @SerializedName("start_year")
    val start_year: String?,
    @SerializedName("start_month")
    val start_month: String?,
    @SerializedName("start_day")
    val start_day: String?,
    @SerializedName("start_time")
    val start_time: String?,
    @SerializedName("end_year")
    val end_year: String?,
    @SerializedName("end_month")
    val end_month: String?,
    @SerializedName("end_day")
    val end_day: String?,
    @SerializedName("end_time")
    val end_time: String?,
    @SerializedName("allDayCheck")
    val allDayCheck: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("place_code")
    val place_code: String?,
    @SerializedName("mission_code")
    val mission_code: String?,
)

