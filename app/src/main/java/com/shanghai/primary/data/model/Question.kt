package com.shanghai.primary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: Subject,
    val grade: Int,
    val prompt: String,
    val optionA: String?,
    val optionB: String?,
    val optionC: String?,
    val optionD: String?,
    val answerIndex: Int,         // 0..3
    val imageEmoji: String? = null,
    val hint: String? = null
)
