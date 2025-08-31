package com.example.odyssey.data.models


data class OpenAIRequest(
    val model: String = "gpt-3.5-turbo",
    val message: List<ChatMessage>,
    val maxTokens: Int = 1500,
    val temperature: Double = 0.7
)

data class ChatMessage(
    val role : String,
    val content: String
)

data class OpenAIResponse(
    val choices: List<ChatChoice>
)

data class ChatChoice(
    val message: ChatMessage
)

