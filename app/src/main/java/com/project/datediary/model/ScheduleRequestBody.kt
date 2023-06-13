package com.project.datediary.model

data class ScheduleRequestBody(
    val couple_index: String?,
   // val schedule_index: String?,
    val start_year: String?,
    val start_month: String?,
    val start_day: String?,
    val start_time: String?,
    val end_year: String?,
    val end_month: String?,
    val end_day: String?,
    val end_time: String?,
    val allDayCheck: String?,
    val title: String?,
    val contents: String?,
    val place_code: String?,
    val mission_code: String?,
    val name: String?,
    val timestamp2: String?
)
