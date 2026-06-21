package com.shanghai.primary

import android.app.Application
import com.shanghai.primary.data.db.AppDatabase
import com.shanghai.primary.data.repo.ProgressRepository
import com.shanghai.primary.data.repo.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class App : Application() {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var db: AppDatabase
        private set
    lateinit var questionRepo: QuestionRepository
        private set
    lateinit var progressRepo: ProgressRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = AppDatabase.get(this)
        questionRepo = QuestionRepository(db)
        progressRepo = ProgressRepository(db)

        appScope.launch { questionRepo.seedIfEmpty() }
    }

    companion object {
        @Volatile private var instance: App? = null
        fun get(): App = instance!!
    }
}
