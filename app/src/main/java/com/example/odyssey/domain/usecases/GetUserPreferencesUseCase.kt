package com.example.odyssey.domain.usecases

import com.example.odyssey.data.models.UserPreferences
import com.example.odyssey.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserPreferencesUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke() : Flow<UserPreferences> {
        return repository.getUserPreferences();
    }
}