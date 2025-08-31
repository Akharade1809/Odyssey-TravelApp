package com.example.odyssey.data.remote

import com.example.odyssey.data.models.OpenAIRequest
import com.example.odyssey.data.models.OpenAIResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AIPlanningApiService {

    @POST("v1/chat/completions")
    suspend fun generateItinerary(
        @Body request: OpenAIRequest,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<OpenAIResponse>

    companion object {
        const val BASE_URL = "https://api.openai.com/"
        const val API_KEY = "YOUR_OPENAI_API_KEY" // Store in BuildConfig
    }
}