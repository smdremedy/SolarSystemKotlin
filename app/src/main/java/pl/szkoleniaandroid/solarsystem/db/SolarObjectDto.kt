package pl.szkoleniaandroid.solarsystem.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.szkoleniaandroid.solarsystem.api.model.ObjectType

@Entity(tableName = "solar_objects")
data class SolarObjectDto(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "object_type") val objectType: ObjectType,
    @ColumnInfo(name = "orbits_id") val orbitsId: String?,
    @ColumnInfo(name = "video_id") val videoId: String?
)
