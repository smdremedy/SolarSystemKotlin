package pl.szkoleniaandroid.solarsystem.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SolarObjectDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(solarObjectDto: SolarObjectDto)

    @Query("SELECT * FROM solar_objects WHERE id = :id")
    suspend fun getById(id: String): SolarObjectDto?
    @Query("SELECT * FROM solar_objects WHERE object_type = 'OTHER'")
    suspend fun getOthers(): List<SolarObjectDto>
}
