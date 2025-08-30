package com.example.odyssey.domain.usecases

import com.example.odyssey.data.models.Destination
import com.example.odyssey.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow

class GetDestinationByCategory(private val repository: DestinationRepository) {
    suspend operator fun invoke(category: String, latitude: Double? = null, longitude: Double? = null): Flow<List<Destination>> {
        return repository.getDestinationByCategory(category, latitude, longitude)
    }
}