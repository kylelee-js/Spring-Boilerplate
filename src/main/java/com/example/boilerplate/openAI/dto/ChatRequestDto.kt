package com.example.boilerplate.openAI.dto

data class ChatRequestDto(
    val model: String,
    val messages: List<MessageDto>,
    val tools: List<ToolDto>? = null
) {
    data class MessageDto(
        val role: String,
        val content: String
    )

    data class ToolDto(
        val type: String,
        val function: Map<String, Any>
    )

    data class FunctionDefinition(
        val name: String,
        val description: String? = null,
        val parameters: Map<String, Any>? = null,
        val strict: Boolean? = null
    )
}