package com.shanghai.primary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shanghai.primary.data.model.Progress
import com.shanghai.primary.data.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress")
    fun observeAll(): Flow<List<Progress>>

    @Query("SELECT * FROM progress WHERE subject = :subject LIMIT 1")
    suspend fun get(subject: Subject): Progress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: Progress)
}
