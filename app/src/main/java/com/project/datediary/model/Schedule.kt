package com.project.datediary.model

data class Schedule(
    val couple_index: String,
    val schedule_index: String,
    val sche_start: String,
    val sche_end: String,
    val allDayCheck: String,
    val title: String,
    val contents: String,
    val place_code: String,
    val mission_code: String
)