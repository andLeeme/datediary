package com.project.datediary.model

data class ChatRequestBody(
    val couple_index: String,
    val email: String,
    val sender: String,
    val message: String,
    val timestamp: String,
)