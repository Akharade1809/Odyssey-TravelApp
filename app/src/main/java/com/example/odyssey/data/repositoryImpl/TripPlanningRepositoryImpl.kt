package com.example.odyssey.data.repositoryImpl

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.odyssey.data.remote.AIPlanningApiService
import com.example.odyssey.domain.entities.*
import com.example.odyssey.domain.repository.TripPlanningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate as JavaLocalDate
import java.util.*

class TripPlanningRepositoryImpl(
    private val aiApiService: AIPlanningApiService
) : TripPlanningRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun generateItinerary(userInput: UserTripInput): Flow<Result<TripPlan>> = flow {
        try {
            // Emit mock itinerary first
            emit(Result.success(createMockItinerary(userInput)))

            // Uncomment later when using OpenAI API
            /*
            val prompt = buildItineraryPrompt(userInput)
            val openAIRequest = OpenAIRequest(
                messages = listOf(
                    ChatMessage("system", "You are a professional travel planner AI assistant. Create detailed, personalized itineraries in JSON format."),
                    ChatMessage("user", prompt)
                )
            )

            val response = aiApiService.generateItinerary(
                request = openAIRequest,
                authorization = "Bearer ${AIPlanningApiService.API_KEY}"
            )

            if (response.isSuccessful && response.body() != null) {
                val aiResponse = response.body()!!
                val itineraryJson = aiResponse.choices.firstOrNull()?.message?.content
                val tripPlan = parseAIResponse(itineraryJson, userInput)
                emit(Result.success(tripPlan))
            } else {
                emit(Result.failure(Exception("Failed to generate itinerary")))
            }
            */
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun saveUserTripPlan(tripPlan: TripPlan): Boolean {
        // TODO: Persist to DB
        return true
    }

    override suspend fun getUserTripPlans(): Flow<List<TripPlan>> = flow {
        // TODO: Implement DB retrieval
        emit(emptyList())
    }

    override suspend fun deleteTripPlan(tripId: String): Boolean {
        // TODO: Implement DB deletion
        return true
    }

    private fun buildItineraryPrompt(userInput: UserTripInput): String = """
        Create a detailed travel itinerary for:
        - Destination: ${userInput.destination}
        - Dates: ${userInput.startDate} to ${userInput.endDate}
        - Travelers: ${userInput.travelers}
        - Budget: $${userInput.budget}
        - Interests: ${userInput.interests.joinToString(", ")}
        - Travel Style: ${userInput.travelStyle}
        - Accommodation: ${userInput.accommodationType}
        
        Please provide a day-by-day itinerary with specific activities, times, locations, and estimated costs.
        Include local tips and hidden gems. Format the response as detailed JSON.
    """.trimIndent()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMockItinerary(userInput: UserTripInput): TripPlan {
        val days = mutableListOf<DayPlan>()
        val startDate: JavaLocalDate = userInput.startDate?.toJavaLocalDate()
            ?: JavaLocalDate.now()
        val endDate: JavaLocalDate = userInput.endDate?.toJavaLocalDate()
            ?: JavaLocalDate.now().plusDays(3)

        var currentDate = startDate
        var dayCounter = 1

        while (!currentDate.isAfter(endDate)) {
            days.add(
                DayPlan(
                    dayNumber = dayCounter,
                    date = currentDate.toKotlinLocalDate(), // ✅ conversion
                    theme = getThemeForDay(dayCounter, userInput.destination),
                    activities = createMockActivitiesForDay(dayCounter, userInput.destination),
                    estimateCost = 100.0 + (dayCounter * 20.0),
                )
            )
            currentDate = currentDate.plusDays(1)
            dayCounter++
        }

        val itinerary = Itinerary(
            title = "Amazing ${userInput.destination} Adventure",
            description = "A carefully crafted itinerary showcasing the best of ${userInput.destination}",
            days = days,
            totalEstimatedCost = days.sumOf { it.estimateCost },
            tips = getMockTips(userInput.destination)
        )

        return TripPlan(
            id = UUID.randomUUID().toString(),
            title = itinerary.title,
            destination = userInput.destination,
            startDate = startDate.toKotlinLocalDate(), // ✅ proper conversion
            endDate = endDate.toKotlinLocalDate(),
            travelers = userInput.travelers,
            budget = userInput.budget,
            currency = "USD",
            itinerary = itinerary,
            isGenerated = true
        )
    }

    private fun getThemeForDay(dayNumber: Int, destination: String): String {
        val themes = listOf(
            "Arrival & Exploration",
            "Cultural Immersion",
            "Adventure & Activities",
            "Local Experiences",
            "Relaxation & Departure"
        )
        return themes.getOrElse(dayNumber - 1) { "Exploration Day" }
    }

    private fun createMockActivitiesForDay(dayNumber: Int, destination: String): List<Activity> = listOf(
        Activity(
            id = "activity_${dayNumber}_1",
            time = "09:00 AM",
            title = "Explore Historic Downtown",
            location = "City Center, $destination",
            duration = "3 hours",
            description = "Discover the rich history and architecture of $destination's historic district",
            category = ActivityCategory.SIGHTSEEING,
            estimatedCost = 0.0,
            imageUrl = "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?w=800",
            tips = listOf("Wear comfortable walking shoes", "Bring a camera")
        ),
        Activity(
            id = "activity_${dayNumber}_2",
            time = "01:00 PM",
            title = "Local Cuisine Experience",
            location = "Food District, $destination",
            duration = "2 hours",
            description = "Taste authentic local dishes and traditional flavors",
            category = ActivityCategory.FOOD,
            estimatedCost = 40.0,
            imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800",
            tips = listOf("Try the local specialty", "Ask locals for recommendations")
        ),
        Activity(
            id = "activity_${dayNumber}_3",
            time = "04:00 PM",
            title = "Cultural Museum Visit",
            location = "Cultural District, $destination",
            duration = "2 hours",
            description = "Immerse yourself in the local culture and art",
            category = ActivityCategory.CULTURE,
            estimatedCost = 15.0,
            imageUrl = "https://images.unsplash.com/photo-1566127444979-b3d2b654e1d8?w=800",
            tips = listOf("Check for guided tours", "Audio guides available")
        )
    )

    private fun getMockTips(destination: String): List<String> = listOf(
        "💡 Download offline maps before exploring",
        "🌦️ Check weather conditions daily",
        "💳 Carry both cash and cards",
        "📱 Learn basic local phrases",
        "🎒 Pack light and comfortable",
        "🚗 Use local transportation",
        "📸 Respect photography rules at attractions"
    )
}

// ✅ helper extension
@RequiresApi(Build.VERSION_CODES.O)
private fun LocalDate.toJavaLocalDate(): JavaLocalDate =
    JavaLocalDate.of(year, monthNumber, dayOfMonth)
