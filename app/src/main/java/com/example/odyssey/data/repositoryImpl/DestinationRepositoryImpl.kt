package com.example.odyssey.data.repositoryImpl

import com.example.odyssey.core.database.TravelDatabase
import com.example.odyssey.data.local.extentions.toDomain
import com.example.odyssey.data.local.extentions.toEntity
import com.example.odyssey.data.models.Destination
import com.example.odyssey.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DestinationRepositoryImpl(
    private val database: TravelDatabase
) : DestinationRepository {
    override suspend fun getPopularDestination(): Flow<List<Destination>> {
        return flow {
            database.destinationDao().getPopularDestinations().collect { entites ->
                if (entites.isEmpty()) {
                    populateDatabase()
                    emit(getMockDestinations().filter { it.isPopular })
                } else {
                    emit(entites.map { it.toDomain() })
                }
            }
        }
    }

    override suspend fun getDestinationByCategory(category: String): Flow<List<Destination>> {
        return flow {
            database.destinationDao().getDestinationsByCategory(category).collect { entities ->
                if (entities.isEmpty()){
                    populateDatabase()
                    emit(getMockDestinations().filter { it.category == category })
                }else{
                    emit(entities.map { it.toDomain() })
                }
            }
        }
    }

    override suspend fun searchDestinations(query: String): Flow<List<Destination>> {
        return flow {
            database.destinationDao().searchDestinations("%$query%").collect { entities ->
                if (entities.isEmpty()) {
                    populateDatabase()
                    emit(getMockDestinations().filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.country.contains(query, ignoreCase = true)
                    })
                } else {
                    emit(entities.map { it.toDomain() })
                }
            }
        }
    }
    override suspend fun toggleFavorite(destinationId: String): Boolean {
        return try {
            database.destinationDao().updateFavoriteStatus(destinationId, true)
            true
        } catch (e: Exception) {
            false
        }
    }




    //helper functions
    private suspend fun populateDatabase() {
        val mockData = getMockDestinations().map { it.toEntity() }
        database.destinationDao().insertDestinations(mockData)
    }

    private fun getMockDestinations(): List<Destination> {
        return listOf(
            Destination(
                id = "1",
                name = "Bali",
                country = "Indonesia",
                description = "Tropical paradise with stunning beaches and rich culture",
                imageUrl = "https://images.unsplash.com/photo-1544366937-7b11ce9a0b7c",
                rating = 4.8f,
                price = 1200.0,
                currency = "USD",
                category = "Beach",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "2",
                name = "Tokyo",
                country = "Japan",
                description = "Modern metropolis with ancient traditions",
                imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf",
                rating = 4.9f,
                price = 1800.0,
                currency = "USD",
                category = "City",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "3",
                name = "Santorini",
                country = "Greece",
                description = "Stunning white buildings overlooking the Aegean Sea",
                imageUrl = "https://images.unsplash.com/photo-1613395877344-13d4a8e0d49e",
                rating = 4.7f,
                price = 1500.0,
                currency = "USD",
                category = "Beach",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "4",
                name = "Machu Picchu",
                country = "Peru",
                description = "Ancient Incan citadel high in the Andes Mountains",
                imageUrl = "https://images.unsplash.com/photo-1587595431973-160d0d94add1",
                rating = 4.6f,
                price = 2200.0,
                currency = "USD",
                category = "Adventure",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "5",
                name = "Dubai",
                country = "UAE",
                description = "Luxury destination with futuristic architecture",
                imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c",
                rating = 4.5f,
                price = 2000.0,
                currency = "USD",
                category = "Luxury",
                isPopular = false,
                isFavorite = false
            )
        )
    }
}


