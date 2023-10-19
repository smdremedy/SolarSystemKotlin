package pl.szkoleniaandroid.solarsystem.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SolarObjectDto::class], version = 1)
@TypeConverters(ObjectTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun solarObjectDao(): SolarObjectDao
}