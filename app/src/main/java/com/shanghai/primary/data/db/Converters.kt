package com.shanghai.primary.data.db

import androidx.room.TypeConverter
import com.shanghai.primary.data.model.Subject

class Converters {

    @TypeConverter
    fun fromSubject(subject: Subject): String = subject.name

    @TypeConverter
    fun toSubject(name: String): Subject = Subject.valueOf(name)
}
