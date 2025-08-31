package com.example.odyssey.domain.usecases

import com.example.odyssey.domain.entities.TripPlan
import com.example.odyssey.domain.repository.TripPlanningRepository

class SaveUserTripPlanUseCase(private val repository: TripPlanningRepository) {
    suspend operator fun invoke(tripPlan: TripPlan) : Boolean {
        return repository.saveUserTripPlan(tripPlan)
    }
}