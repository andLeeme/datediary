package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class Static3ResponseBody(

    @SerializedName("month")
    val month: String?,
    @SerializedName("count")
    val count: String?,

)

