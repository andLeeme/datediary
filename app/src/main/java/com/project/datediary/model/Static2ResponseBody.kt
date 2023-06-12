package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class Static2ResponseBody(

    @SerializedName("placeCode")
    val startYear: String?,
    @SerializedName("count")
    val count: String?,


)

