package com.shanghai.primary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: Subject,
    val grade: Int,
    val gameType: String = GameType.QUIZ,  // QUIZ / FLASHCARD / DRAG_MATCH / WORD_SCRAMBLE / TIMED
    val prompt: String,
    val optionA: String? = null,
    val optionB: String? = null,
    val optionC: String? = null,
    val optionD: String? = null,
    val answerIndex: Int = 0,         // 0..3 for QUIZ
    val imageEmoji: String? = null,
    val hint: String? = null,
    // DRAG_MATCH fields
    val pairLeft: String? = null,     // e.g. "大"
    val pairRight: String? = null,    // e.g. "小"
    // WORD_SCRAMBLE fields
    val scrambledWord: String? = null, // e.g. "ppale"
    val correctWord: String? = null,   // e.g. "apple"
)

object GameType {
    const val QUIZ = "QUIZ"
    const val FLASHCARD = "FLASHCARD"
    const val DRAG_MATCH = "DRAG_MATCH"
    const val WORD_SCRAMBLE = "WORD_SCRAMBLE"
    const val TIMED = "TIMED"
}
