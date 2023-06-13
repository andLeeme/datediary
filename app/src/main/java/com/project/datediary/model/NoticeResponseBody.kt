package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class NoticeResponseBody(
    @SerializedName("couple_index")
    val couple_index: String,

    @SerializedName("timestamp2")
    val timestamp: String,


    @SerializedName("name2")
    val name: String,

    @SerializedName("type2")
    val type: String,

    @SerializedName("month")
    val month: String,

    @SerializedName("day")
    val day: String,


)