package com.example.odyssey.domain.usecases

import com.example.odyssey.data.models.UserPreferences
import com.example.odyssey.domain.repository.UserPreferencesRepository

class UpdateUserPreferencesUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(userPreferences : UserPreferences){
        return repository.updateUserPreferences(userPreferences)
    }
}