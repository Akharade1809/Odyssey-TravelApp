package com.example.odyssey.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.odyssey.data.local.dao.DestinationDao
import com.example.odyssey.data.local.entity.DestinationEntity

@Database(
    entities = [DestinationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class TravelDatabase : RoomDatabase() {
    abstract fun destinationDao(): DestinationDao

    companion object {
        const val DATABASE_NAME = "odyssey_database"
    }
}
