package com.shanghai.primary.data.repo

import com.shanghai.primary.data.db.AppDatabase
import com.shanghai.primary.data.model.Subject
import com.shanghai.primary.data.model.WrongQuestion

class WrongQuestionRepository(private val db: AppDatabase) {

    suspend fun recordWrong(questionId: Int, subject: Subject, grade: Int) {
        val exists = db.wrongQuestionDao().exists(questionId)
        if (exists) {
            val existing = db.wrongQuestionDao().getAll().find { it.questionId == questionId }
            if (existing != null) {
                db.wrongQuestionDao().deleteByQuestionId(questionId)
                db.wrongQuestionDao().insert(
                    existing.copy(
                        wrongCount = existing.wrongCount + 1,
                        lastWrongAt = System.currentTimeMillis()
                    )
                )
            }
        } else {
            db.wrongQuestionDao().insert(
                WrongQuestion(
                    questionId = questionId,
                    subject = subject,
                    grade = grade
                )
            )
        }
    }

    suspend fun getWrongQuestions(subject: Subject? = null): List<WrongQuestion> {
        return if (subject != null) {
            db.wrongQuestionDao().getBySubject(subject)
        } else {
            db.wrongQuestionDao().getAll()
        }
    }

    suspend fun deleteWrongQuestion(questionId: Int) {
        db.wrongQuestionDao().deleteByQuestionId(questionId)
    }

    suspend fun getQuestionIds(subject: Subject? = null): List<Int> {
        val list = if (subject != null) {
            db.wrongQuestionDao().getBySubject(subject)
        } else {
            db.wrongQuestionDao().getAll()
        }
        return list.map { it.questionId }
    }
}
