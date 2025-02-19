package com.example.boilerplate.openAI.dto

data class ChatRequestDto(
    val model: String,
    val messages: List<MessageDto>
) {
    data class MessageDto(
        val role: String,
        val content: String
    )
}