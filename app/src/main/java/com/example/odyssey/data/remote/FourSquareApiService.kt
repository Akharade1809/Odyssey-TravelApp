package com.example.odyssey.data.remote

import com.example.odyssey.data.models.FoursquareResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FourSquareApiService {

    @GET("places/search")
    suspend fun searchNearbyPlaces(
        @Query("ll") latLong: String, // format: "lat,lng"
        @Query("radius") radius: Int? = null,
        @Query("categories") categories: String? = null,
        @Query("limit") limit: Int? = 20,
        @Query("sort") sort: String? = "RELEVANCE"
        ): Response<FoursquareResponse>

    @GET("geotagging/candidates")
    suspend fun getNearbyPlaces(
        @Query("ll") latLong: String,
        @Query("limit") limit: Int = 20,
        @Query("fields") fields: String = "name,location,geocodes,categories,rating,price,photos,distance",
    ): Response<FoursquareResponse>

    companion object {
        const val BASE_URL = "https://places-api.foursquare.com/"

        // Store this securely in BuildConfig or secure storage
        const val API_KEY = "YOUR_API"
        // Popular Foursquare category IDs for travel

    }

     object Categories {
        const val RESTAURANTS = "13065" // Food & Drink > Restaurant
        const val CAFES = "13035" // Food & Drink > Cafe
        const val ATTRACTIONS = "16000" // Landmarks and Outdoors
        const val HOTELS = "19014" // Travel & Transportation > Hotel
        const val NIGHTLIFE = "10000" // Arts & Entertainment > Nightlife
        const val SHOPPING = "17000" // Retail
        const val MUSEUMS = "10019" // Arts & Entertainment > Museum
        const val PARKS = "16013" // Landmarks and Outdoors > Park
    }
}