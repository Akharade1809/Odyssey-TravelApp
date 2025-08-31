package com.example.odyssey.domain.usecases

import com.example.odyssey.domain.entities.TripPlan
import com.example.odyssey.domain.repository.TripPlanningRepository
import kotlinx.coroutines.flow.Flow

class GetUserTripPlansUseCase(private val repository: TripPlanningRepository) {
    suspend operator fun invoke() : Flow<List<TripPlan>> {
        return repository.getUserTripPlans()
    }
}