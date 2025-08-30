package com.example.odyssey.domain.repository

import com.example.odyssey.data.models.Destination
import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    // Updated to accept optional location parameters
    suspend fun getPopularDestination(latitude: Double? = null, longitude: Double? = null): Flow<List<Destination>>
    suspend fun getDestinationByCategory(category: String, latitude: Double? = null, longitude: Double? = null): Flow<List<Destination>>
    suspend fun searchDestinations(query: String): Flow<List<Destination>>
    suspend fun toggleFavorite(destinationId: String): Boolean

    // New method for getting nearby places specifically
    suspend fun getNearbyRecommendations(latitude: Double, longitude: Double): Flow<List<Destination>>
}
