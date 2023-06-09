package com.project.datediary.model

import com.google.gson.annotations.SerializedName

data class ChatResponseBody(
    @SerializedName("couple_index")
    val couple_index: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("sender")
    val sender: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("timestamp")
    val timestamp: String,
)