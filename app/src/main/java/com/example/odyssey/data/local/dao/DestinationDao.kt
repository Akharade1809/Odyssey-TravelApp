package com.example.odyssey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.odyssey.data.local.entity.DestinationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {

    @Query("SELECT * FROM destinations WHERE isPopular = 1")
    fun getPopularDestinations(): Flow<List<DestinationEntity>>

    @Query("SELECT * FROM destinations WHERE category = :category")
    fun getDestinationsByCategory(category: String): Flow<List<DestinationEntity>>

    @Query("SELECT * FROM destinations WHERE name LIKE :query OR country LIKE :query")
    fun searchDestinations(query: String): Flow<List<DestinationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestinations(destinations: List<DestinationEntity>)

    @Query("UPDATE destinations SET isFavorite = :isFavorite WHERE id = :destinationId")
    suspend fun updateFavoriteStatus(destinationId: String, isFavorite: Boolean)

    @Query("SELECT * FROM destinations")
    suspend fun getAllDestinations(): List<DestinationEntity>

    @Query("DELETE FROM destinations")
    suspend fun clearAll()
}
