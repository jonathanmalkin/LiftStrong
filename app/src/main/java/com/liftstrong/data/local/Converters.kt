package com.liftstrong.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Type converters for Room database.
 * 
 * This class provides methods to convert between complex types and primitive types
 * that can be stored in the database.
 */
class Converters {
    
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    
    /**
     * Converts a LocalDate to a String.
     */
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }
    
    /**
     * Converts a String to a LocalDate.
     */
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }
    
    /**
     * Converts a LocalDateTime to a String.
     */
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(dateTimeFormatter)
    }
    
    /**
     * Converts a String to a LocalDateTime.
     */
    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }
    
    /**
     * Converts a list of Strings to a single String.
     */
    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }
    
    /**
     * Converts a single String to a list of Strings.
     */
    @TypeConverter
    fun toStringList(string: String?): List<String>? {
        return string?.split(",")?.map { it.trim() }
    }
}
