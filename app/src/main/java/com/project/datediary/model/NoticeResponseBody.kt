package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class NoticeResponseBody(
    @SerializedName("couple_index")
    val couple_index: String,

    @SerializedName("time_stamp")
    val time_stamp: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("type")
    val type: String,

)