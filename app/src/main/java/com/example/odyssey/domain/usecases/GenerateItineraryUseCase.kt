package com.example.odyssey.domain.usecases

import com.example.odyssey.domain.entities.TripPlan
import com.example.odyssey.domain.entities.UserTripInput
import com.example.odyssey.domain.repository.TripPlanningRepository
import kotlinx.coroutines.flow.Flow

class GenerateItineraryUseCase(private val repository: TripPlanningRepository) {
    suspend operator fun invoke(userTripInput: UserTripInput) : Flow<Result<TripPlan>>{
        return repository.generateItinerary(userTripInput)
    }
}