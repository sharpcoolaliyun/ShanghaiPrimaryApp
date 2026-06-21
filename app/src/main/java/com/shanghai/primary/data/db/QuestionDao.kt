package com.shanghai.primary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.Subject

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Question>)

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun totalCount(): Int

    /** 在最早发现的可用年级（<= 目标年级）中随机抽题 */
    @Query(
        """
        SELECT * FROM questions
        WHERE subject = :subject AND grade <= :grade
        ORDER BY RANDOM() LIMIT :size
        """
    )
    suspend fun random(subject: Subject, grade: Int, size: Int): List<Question>
}
