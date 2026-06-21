package com.shanghai.primary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** 答题进度（小星星） */
@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey val subject: Subject,
    val totalAnswered: Int = 0,
    val totalCorrect: Int = 0,
    val stars: Int = 0       // 每答对一题 +1，连续 5 题 +2 奖励见 Repo
)
