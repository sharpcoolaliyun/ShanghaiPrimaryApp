package com.shanghai.primary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 错题记录表。
 * 每次答错时插入一条记录，复习后可删除。
 */
@Entity(tableName = "wrong_questions")
data class WrongQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: Int,
    val subject: Subject,
    val grade: Int,
    val wrongCount: Int = 1,
    val lastWrongAt: Long = System.currentTimeMillis()
)
