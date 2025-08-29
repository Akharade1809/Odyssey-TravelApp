package com.example.odyssey.domain.repository

import com.example.odyssey.data.models.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun getUserPreferences() : Flow<UserPreferences>
    suspend fun updateUserPreferences(preferences: UserPreferences)
}