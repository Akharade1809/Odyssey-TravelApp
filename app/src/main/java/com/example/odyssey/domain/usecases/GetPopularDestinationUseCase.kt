package com.example.odyssey.domain.usecases

import com.example.odyssey.data.models.Destination
import com.example.odyssey.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow

class GetPopularDestinationUseCase(private val repository: DestinationRepository) {
    suspend operator fun invoke(): Flow<List<Destination>> {
        return repository.getPopularDestination()
    }
}