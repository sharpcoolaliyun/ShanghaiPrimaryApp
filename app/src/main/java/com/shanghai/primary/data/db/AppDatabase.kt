package com.shanghai.primary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shanghai.primary.data.model.Progress
import com.shanghai.primary.data.model.Question

@Database(
    entities = [Question::class, Progress::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "shanghai_primary.db"
            ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }
}
