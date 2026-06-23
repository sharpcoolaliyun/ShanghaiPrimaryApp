package com.shanghai.primary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shanghai.primary.data.model.Progress
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.WrongQuestion

@Database(
    entities = [Question::class, Progress::class, WrongQuestion::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun progressDao(): ProgressDao
    abstract fun wrongQuestionDao(): WrongQuestionDao

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
