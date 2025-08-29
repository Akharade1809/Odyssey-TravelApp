package com.example.odyssey.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences // Import the correct Preferences class
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.odyssey.data.models.UserPreferences
import com.example.odyssey.domain.repository.UserPreferencesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Extension property to create DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepositoryImpl(
    private val context: Context
) : UserPreferencesRepository {

    private object PreferencesKeys {
        val TRAVEL_STYLE = stringPreferencesKey("travel_style")
        val PREFERRED_BUDGET = doublePreferencesKey("preferred_budget")
        val CURRENCY = stringPreferencesKey("currency")
        val FAVORITE_CATEGORIES = stringPreferencesKey("favorite_categories")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        val USER_NAME = stringPreferencesKey("user_name")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val THEME_MODE = stringPreferencesKey("theme_mode") // "light", "dark", "system"
        val LANGUAGE = stringPreferencesKey("language")
        val DISTANCE_UNIT = stringPreferencesKey("distance_unit") // "km", "miles"
    }

    private val gson = Gson()

    override suspend fun getUserPreferences(): Flow<UserPreferences> {
        return context.dataStore.data
            .catch { exception ->
                // Handle IOException when reading preferences
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                UserPreferences(
                    travelStyle = preferences[PreferencesKeys.TRAVEL_STYLE] ?: "adventurer",
                    preferredBudget = preferences[PreferencesKeys.PREFERRED_BUDGET] ?: 1000.0,
                    currency = preferences[PreferencesKeys.CURRENCY] ?: "USD",
                    favoriteCategories = parseFavoriteCategories(
                        preferences[PreferencesKeys.FAVORITE_CATEGORIES]
                    ),
                    isFirstLaunch = preferences[PreferencesKeys.IS_FIRST_LAUNCH] ?: true,
                    userName = preferences[PreferencesKeys.USER_NAME] ?: "Explorer",
                    notificationEnabled = preferences[PreferencesKeys.NOTIFICATION_ENABLED] ?: true,
                    themeMode = preferences[PreferencesKeys.THEME_MODE] ?: "system",
                    language = preferences[PreferencesKeys.LANGUAGE] ?: "en",
                    distanceUnit = preferences[PreferencesKeys.DISTANCE_UNIT] ?: "km"
                )
            }
    }

    override suspend fun updateUserPreferences(preferences: UserPreferences) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TRAVEL_STYLE] = preferences.travelStyle
            prefs[PreferencesKeys.PREFERRED_BUDGET] = preferences.preferredBudget
            prefs[PreferencesKeys.CURRENCY] = preferences.currency
            prefs[PreferencesKeys.FAVORITE_CATEGORIES] = serializeFavoriteCategories(preferences.favoriteCategories)
            prefs[PreferencesKeys.IS_FIRST_LAUNCH] = preferences.isFirstLaunch
            prefs[PreferencesKeys.USER_NAME] = preferences.userName
            prefs[PreferencesKeys.NOTIFICATION_ENABLED] = preferences.notificationEnabled
            prefs[PreferencesKeys.THEME_MODE] = preferences.themeMode
            prefs[PreferencesKeys.LANGUAGE] = preferences.language
            prefs[PreferencesKeys.DISTANCE_UNIT] = preferences.distanceUnit
        }
    }

    // Individual update methods for specific preferences
    suspend fun updateTravelStyle(travelStyle: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TRAVEL_STYLE] = travelStyle
        }
    }

    suspend fun updateBudget(budget: Double) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_BUDGET] = budget
        }
    }

    suspend fun updateCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENCY] = currency
        }
    }

    suspend fun updateFavoriteCategories(categories: List<String>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FAVORITE_CATEGORIES] = serializeFavoriteCategories(categories)
        }
    }

    suspend fun updateUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = userName
        }
    }

    suspend fun updateNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] = enabled
        }
    }

    suspend fun updateThemeMode(themeMode: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = themeMode
        }
    }

    suspend fun markOnboardingComplete() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LAUNCH] = false
        }
    }

    suspend fun clearAllPreferences() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Helper methods for serialization
    private fun serializeFavoriteCategories(categories: List<String>): String {
        return gson.toJson(categories)
    }

    private fun parseFavoriteCategories(json: String?): List<String> {
        if (json.isNullOrEmpty()) {
            return getDefaultCategories()
        }

        return try {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type) ?: getDefaultCategories()
        } catch (e: Exception) {
            getDefaultCategories()
        }
    }

    private fun getDefaultCategories(): List<String> {
        return listOf("Adventure", "Beach", "City", "Culture", "Nature")
    }
}
