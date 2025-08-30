package com.example.odyssey.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey : String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Accept", "application/json")
            .addHeader("X-Places-Api-Version", "2025-06-17")
            .build()

        return chain.proceed(request)
    }

}