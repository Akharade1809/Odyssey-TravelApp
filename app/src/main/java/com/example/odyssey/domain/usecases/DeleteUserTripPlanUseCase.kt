package com.example.odyssey.domain.usecases

import com.example.odyssey.domain.repository.TripPlanningRepository

class DeleteUserTripPlanUseCase(private val repository: TripPlanningRepository) {
    suspend operator fun invoke(tripId : String) : Boolean {
        return repository.deleteTripPlan(tripId = tripId)
    }
}