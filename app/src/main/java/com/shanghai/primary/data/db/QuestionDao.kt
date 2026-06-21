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

    @Query(
        """
        SELECT * FROM questions
        WHERE subject = :subject AND grade <= :grade
        ORDER BY RANDOM() LIMIT :size
        """
    )
    suspend fun random(subject: Subject, grade: Int, size: Int): List<Question>

    @Query(
        """
        SELECT * FROM questions
        WHERE subject = :subject AND grade <= :grade AND gameType = :gameType
        ORDER BY RANDOM() LIMIT :size
        """
    )
    suspend fun randomByType(subject: Subject, grade: Int, gameType: String, size: Int): List<Question>
}
