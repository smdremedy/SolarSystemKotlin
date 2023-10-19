package pl.szkoleniaandroid.solarsystem.db

import androidx.room.TypeConverter
import pl.szkoleniaandroid.solarsystem.api.model.ObjectType

class ObjectTypeConverter {

    @TypeConverter
    fun fromObjectType(objectType: ObjectType): String {
        return objectType.name
    }

    @TypeConverter
    fun toObjectType(value: String): ObjectType {
        return ObjectType.valueOf(value)
    }
}