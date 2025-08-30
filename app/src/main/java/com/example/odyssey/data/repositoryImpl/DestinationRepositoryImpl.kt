package com.example.odyssey.data.repositoryImpl

import android.util.Log
import com.example.odyssey.core.location.LocationManager
import com.example.odyssey.data.models.Destination
import com.example.odyssey.data.models.FoursquarePlace
import com.example.odyssey.data.remote.FourSquareApiService
import com.example.odyssey.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

class DestinationRepositoryImpl(
    private val foursquareApiService: FourSquareApiService,
    private val locationManager: LocationManager
) : DestinationRepository {

    override suspend fun getPopularDestination(latitude: Double?, longitude: Double?): Flow<List<Destination>> = flow {
        try {
            // First emit cached/mock data for immediate UI response
            emit(getMockDestinations().filter { it.isPopular })

            // Then fetch real location-based data if location is available
            val currentLocation = when {
                latitude != null && longitude != null -> Pair(latitude, longitude)
                else -> {
                    val location = locationManager.getCurrentLocation()
                    if (location != null) Pair(location.latitude, location.longitude) else null
                }
            }
            Log.d("DestinationRepository", "getPopularDestination: currentLocation : $currentLocation ")

            if (currentLocation != null) {
                val nearbyPlaces = fetchNearbyRecommendations(currentLocation.first, currentLocation.second)
                Log.d("DestinationRepository", "getPopularDestination: nearByPlaces : $nearbyPlaces")
                if (nearbyPlaces.isNotEmpty()) {
                    emit(nearbyPlaces)
                }
            }
        } catch (e: Exception) {
            Log.e("DestinationRepository", "getPopularDestination: ${e.message} ", )
            emit(getMockDestinations().filter { it.isPopular })
        }
    }

    override suspend fun getDestinationByCategory(
        category: String,
        latitude: Double?,
        longitude: Double?
    ): Flow<List<Destination>> = flow {
        try {
            // Get location coordinates
            val currentLocation = when {
                latitude != null && longitude != null -> Pair(latitude, longitude)
                else -> {
                    val location = locationManager.getCurrentLocation()
                    if (location != null) Pair(location.latitude, location.longitude) else null
                }
            }

            if (currentLocation != null) {
                val categoryId = mapCategoryToFoursquareId(category)
                val places = fetchPlacesByCategory(currentLocation.first, currentLocation.second, categoryId)
                emit(places)
            } else {
                // Fallback to mock data filtered by category
                emit(getMockDestinations().filter { it.category.equals(category, ignoreCase = true) })
            }
        } catch (e: Exception) {
            emit(getMockDestinations().filter { it.category.equals(category, ignoreCase = true) })
        }
    }

    override suspend fun searchDestinations(query: String): Flow<List<Destination>> = flow {
        // For now, search in mock data. Can be enhanced to use Foursquare text search
        emit(getMockDestinations().filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.country.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        })
    }

    override suspend fun toggleFavorite(destinationId: String): Boolean {
        return try {
            // TODO: Implement favorite persistence (Room database or SharedPreferences)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getNearbyRecommendations(latitude: Double, longitude: Double): Flow<List<Destination>> = flow {
        try {
            val nearbyPlaces = fetchNearbyRecommendations(latitude, longitude)
            emit(nearbyPlaces)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Private helper functions for Foursquare API integration
    private suspend fun fetchNearbyRecommendations(latitude: Double, longitude: Double): List<Destination> {
        return try {
            val latLng = "$latitude,$longitude"

            // Get diverse categories for travel recommendations
            val travelCategories = listOf(
                FourSquareApiService.Categories.ATTRACTIONS,
                FourSquareApiService.Categories.RESTAURANTS,
                FourSquareApiService.Categories.MUSEUMS,
                FourSquareApiService.Categories.PARKS
            ).joinToString(",")

            val response = foursquareApiService.searchNearbyPlaces(
             latLong = latLng,
            radius = 10000, // 10km
            categories = travelCategories,
            limit = 20,
            sort = "RELEVANCE"
            )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.results.map { foursquarePlace ->
                    Log.d("DestinationRepository", "fetchNearbyRecommendations: response: ${response.body()} ")
                    convertFoursquarePlaceToDestination(foursquarePlace)
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("DestinationRepository", "fetchNearbyRecommendations: ${e.message}", )
            emptyList()
        }
    }

    private suspend fun fetchPlacesByCategory(
        latitude: Double,
        longitude: Double,
        categoryId: String
    ): List<Destination> {
        return try {
            val latLng = "$latitude,$longitude"

            val travelCategories = listOf(
                FourSquareApiService.Categories.ATTRACTIONS,
                FourSquareApiService.Categories.RESTAURANTS,
                FourSquareApiService.Categories.MUSEUMS,
                FourSquareApiService.Categories.PARKS
            ).joinToString(",")

            val response = foursquareApiService.searchNearbyPlaces(
                latLong = latLng,
                radius = 10000, // 10km
                categories = travelCategories,
                limit = 20,
                sort = "RELEVANCE"
            )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.results.map { foursquarePlace ->
                    convertFoursquarePlaceToDestination(foursquarePlace)
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun convertFoursquarePlaceToDestination(place: FoursquarePlace): Destination {
        return Destination(
            id = (place.fsqId ?: 0).toString(),
            name = place.name,
            country = place.location.country ?: place.location.region ?: "Local",
            description = generateDescription(place),
            imageUrl = getPlaceImageUrl(place),
            rating = (place.rating?.toFloat() ?: generateRandomRating()),
            price = estimatePriceFromFoursquare(place.price),
            currency = "INR",
            category = mapFoursquareCategoryToAppCategory(place.categories.firstOrNull()?.name),
            isPopular = true,
            isFavorite = false
        )
    }

    private fun generateDescription(place: FoursquarePlace): String {
        val category = place.categories.firstOrNull()?.name ?: "place"
        val distance = place.distance?.let {
            "${(it / 1000.0).roundToInt()}km away"
        } ?: ""

        val tips = place.tips?.firstOrNull()?.text

        return when {
            tips != null -> tips.take(120) + if (tips.length > 120) "..." else ""
            place.location.locality != null -> "Popular $category in ${place.location.locality} $distance"
            else -> "Highly rated $category $distance"
        }.trim()
    }

    private fun getPlaceImageUrl(place: FoursquarePlace): String {
        return place.photos?.firstOrNull()?.getPhotoUrl("600x400")
            ?: getDefaultImageForCategory(place.categories.firstOrNull()?.name)
    }

    private fun getDefaultImageForCategory(categoryName: String?): String {
        return when {
            categoryName?.contains("restaurant", true) == true ->
                "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=600&h=400&fit=crop&auto=format"
            categoryName?.contains("cafe", true) == true ->
                "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=600&h=400&fit=crop&auto=format"
            categoryName?.contains("museum", true) == true ->
                "https://images.unsplash.com/photo-1566127444979-b3d2b654e1d8?w=600&h=400&fit=crop&auto=format"
            categoryName?.contains("park", true) == true ->
                "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=600&h=400&fit=crop&auto=format"
            categoryName?.contains("hotel", true) == true ->
                "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600&h=400&fit=crop&auto=format"
            else -> "https://images.unsplash.com/photo-1488646953014-85cb44e25828?w=600&h=400&fit=crop&auto=format"
        }
    }

    private fun generateRandomRating(): Float {
        return (3.5 + Math.random() * 1.5).toFloat() // Random rating between 3.5-5.0
    }

    private fun estimatePriceFromFoursquare(priceLevel: Int?): Double {
        return when (priceLevel) {
            1 -> 25.0  // $
            2 -> 50.0  // $$
            3 -> 100.0 // $$$
            4 -> 200.0 // $$$$
            else -> 40.0 // Default moderate price
        }
    }

    private fun mapFoursquareCategoryToAppCategory(categoryName: String?): String {
        return when {
            categoryName?.contains("restaurant", true) == true -> "Food"
            categoryName?.contains("cafe", true) == true -> "Food"
            categoryName?.contains("museum", true) == true -> "Culture"
            categoryName?.contains("park", true) == true -> "Nature"
            categoryName?.contains("hotel", true) == true -> "Hotel"
            categoryName?.contains("bar", true) == true -> "Nightlife"
            categoryName?.contains("shop", true) == true -> "Shopping"
            else -> "Attraction"
        }
    }

    private fun mapCategoryToFoursquareId(category: String): String {
        return when (category.lowercase()) {
            "food" -> FourSquareApiService.Categories.RESTAURANTS
            "culture" -> FourSquareApiService.Categories.MUSEUMS
            "nature" -> FourSquareApiService.Categories.PARKS
            "hotel" -> FourSquareApiService.Categories.HOTELS
            "nightlife" -> FourSquareApiService.Categories.NIGHTLIFE
            "shopping" -> FourSquareApiService.Categories.SHOPPING
            "beach" -> FourSquareApiService.Categories.ATTRACTIONS
            "city" -> FourSquareApiService.Categories.ATTRACTIONS
            "adventure" -> FourSquareApiService.Categories.ATTRACTIONS
            "luxury" -> FourSquareApiService.Categories.HOTELS
            else -> FourSquareApiService.Categories.ATTRACTIONS
        }
    }

    // Keep your existing mock data method but enhance it
    private fun getMockDestinations(): List<Destination> {
        return listOf(
            Destination(
                id = "mock_1",
                name = "Bali",
                country = "Indonesia",
                description = "Tropical paradise with stunning beaches and rich culture",
                imageUrl = "https://images.unsplash.com/photo-1544366937-7b11ce9a0b7c?w=800&auto=format",
                rating = 4.8f,
                price = 1200.0,
                currency = "USD",
                category = "Beach",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "mock_2",
                name = "Tokyo",
                country = "Japan",
                description = "Modern metropolis with ancient traditions",
                imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800&auto=format",
                rating = 4.9f,
                price = 1800.0,
                currency = "USD",
                category = "City",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "mock_3",
                name = "Santorini",
                country = "Greece",
                description = "Stunning white buildings overlooking the Aegean Sea",
                imageUrl = "https://images.unsplash.com/photo-1613395877344-13d4a8e0d49e?w=800&auto=format",
                rating = 4.7f,
                price = 1500.0,
                currency = "USD",
                category = "Beach",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "mock_4",
                name = "Machu Picchu",
                country = "Peru",
                description = "Ancient Incan citadel high in the Andes Mountains",
                imageUrl = "https://images.unsplash.com/photo-1587595431973-160d0d94add1?w=800&auto=format",
                rating = 4.6f,
                price = 2200.0,
                currency = "USD",
                category = "Adventure",
                isPopular = true,
                isFavorite = false
            ),
            Destination(
                id = "mock_5",
                name = "Dubai",
                country = "UAE",
                description = "Luxury destination with futuristic architecture",
                imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?w=800&auto=format",
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
