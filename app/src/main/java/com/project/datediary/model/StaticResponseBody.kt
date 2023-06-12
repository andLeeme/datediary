package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class StaticResponseBody(

    @SerializedName("coupleIndex")
    val coupleIndex: String?,
    @SerializedName("startYear")
    val startYear: String?,
    @SerializedName("startMonth")
    val startMonth: String?,
    @SerializedName("count")
    val count: String?,


)

