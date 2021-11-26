package com.example.todoappcleanarchitecture.data

import androidx.room.TypeConverter
import com.example.todoappcleanarchitecture.data.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(name: String): Priority {
        return Priority.valueOf(name)
    }
}