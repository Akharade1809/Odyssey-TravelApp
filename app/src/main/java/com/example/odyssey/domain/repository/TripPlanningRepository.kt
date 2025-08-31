package com.example.odyssey.domain.repository

import com.example.odyssey.domain.entities.TripPlan
import com.example.odyssey.domain.entities.UserTripInput
import kotlinx.coroutines.flow.Flow

interface TripPlanningRepository {

    suspend fun generateItinerary(userTripInput: UserTripInput): Flow<Result<TripPlan>>
    suspend fun saveUserTripPlan(tripPlan: TripPlan) : Boolean
    suspend fun getUserTripPlans() : Flow<List<TripPlan>>
    suspend fun deleteTripPlan(tripId:String) : Boolean
}