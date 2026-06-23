package com.shanghai.primary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shanghai.primary.data.model.Subject
import com.shanghai.primary.data.model.WrongQuestion

@Dao
interface WrongQuestionDao {

    @Query("SELECT * FROM wrong_questions WHERE subject = :subject ORDER BY lastWrongAt DESC")
    suspend fun getBySubject(subject: Subject): List<WrongQuestion>

    @Query("SELECT * FROM wrong_questions ORDER BY lastWrongAt DESC")
    suspend fun getAll(): List<WrongQuestion>

    @Query("SELECT EXISTS(SELECT 1 FROM wrong_questions WHERE questionId = :questionId)")
    suspend fun exists(questionId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wq: WrongQuestion)

    @Query("DELETE FROM wrong_questions WHERE questionId = :questionId")
    suspend fun deleteByQuestionId(questionId: Int)
}
