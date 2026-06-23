package com.shanghai.primary.data.repo

import com.shanghai.primary.data.db.AppDatabase
import com.shanghai.primary.data.model.Progress
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.Subject
import com.shanghai.primary.data.seed.SeedQuestions
import com.shanghai.primary.data.model.GameType
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val db: AppDatabase) {

    /** 首次启动时灌种子题；用 count 判断，不重复插入 */
    suspend fun seedIfEmpty() {
        if (db.questionDao().totalCount() == 0) {
            db.questionDao().insertAll(SeedQuestions.all())
        }
    }

    suspend fun nextRound(subject: Subject, grade: Int, size: Int = 10): List<Question> =
        db.questionDao().random(subject, grade, size)

    suspend fun nextFlashCardRound(subject: Subject, grade: Int, size: Int = 10): List<Question> =
        db.questionDao().randomByType(subject, grade, GameType.FLASHCARD, size)

    suspend fun getByIds(ids: List<Int>): List<Question> =
        if (ids.isEmpty()) emptyList() else db.questionDao().getByIds(ids)
}

class ProgressRepository(private val db: AppDatabase) {

    fun observeAll(): Flow<List<Progress>> = db.progressDao().observeAll()

    suspend fun bump(subject: Subject, correct: Boolean) {
        val cur = db.progressDao().get(subject) ?: Progress(subject = subject)
        val newAnswered = cur.totalAnswered + 1
        val newCorrect = if (correct) cur.totalCorrect + 1 else cur.totalCorrect
        val starDelta = if (correct) 1 else 0
        // 连续答对 5 题额外 +1 颗星
        val bonus = if (correct && newCorrect % 5 == 0) 1 else 0
        db.progressDao().upsert(
            cur.copy(
                totalAnswered = newAnswered,
                totalCorrect = newCorrect,
                stars = cur.stars + starDelta + bonus
            )
        )
    }

    suspend fun get(subject: Subject): Progress =
        db.progressDao().get(subject) ?: Progress(subject = subject)
}
