package com.example.odyssey.domain.repository

import androidx.room.Query
import com.example.odyssey.data.models.Destination
import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    suspend fun getPopularDestination() : Flow<List<Destination>>
    suspend fun getDestinationByCategory(category : String) : Flow<List<Destination>>
    suspend fun searchDestinations(query: String) : Flow<List<Destination>>
    suspend fun toggleFavorite(destinationId: String): Boolean
}
