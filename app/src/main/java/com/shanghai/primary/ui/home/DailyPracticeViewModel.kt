package com.shanghai.primary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanghai.primary.App
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DailyPracticeState(
    val index: Int = 0,
    val total: Int = 5,
    val current: Question? = null,
    val selected: Int? = null,
    val isAnswered: Boolean = false,
    val score: Int = 0,
    val finished: Boolean = false,
    val bonusClaimed: Boolean = false
)

class DailyPracticeViewModel : ViewModel() {

    private val _state = MutableStateFlow(DailyPracticeState())
    val state: StateFlow<DailyPracticeState> = _state.asStateFlow()

    private var questions: List<Question> = emptyList()
    private var todayKey: String = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            // 从所有科目随机抽5题
            val allSubjects = Subject.values()
            val mixed = mutableListOf<Question>()
            allSubjects.forEach { subject ->
                val qs = App.get().questionRepo.nextRound(subject, 1, 2)
                mixed.addAll(qs)
            }
            questions = if (mixed.size >= 5) mixed.shuffled().take(5) else mixed
            val first = questions.firstOrNull()
            _state.value = DailyPracticeState(
                index = 0,
                total = questions.size,
                current = first
            )
        }
    }

    fun answer(selectedOriginalIndex: Int) {
        val cur = _state.value
        if (cur.isAnswered || cur.current == null) return
        val correct = selectedOriginalIndex == cur.current.answerIndex
        val newScore = if (correct) cur.score + 1 else cur.score
        viewModelScope.launch {
            App.get().progressRepo.bump(cur.current.subject, correct)
        }
        _state.value = cur.copy(
            selected = selectedOriginalIndex,
            isAnswered = true,
            score = newScore
        )
    }

    fun next() {
        val cur = _state.value
        val nextIdx = cur.index + 1
        if (nextIdx >= questions.size) {
            _state.value = cur.copy(finished = true)
        } else {
            _state.value = DailyPracticeState(
                index = nextIdx,
                total = questions.size,
                current = questions[nextIdx],
                score = cur.score
            )
        }
    }

    fun claimBonus(): Int {
        val cur = _state.value
        if (cur.bonusClaimed || !cur.finished) return 0
        val bonus = 3 // 每日一练完成奖励3颗星
        viewModelScope.launch {
            val subjects = Subject.values()
            subjects.forEach { subject ->
                repeat(bonus) {
                    App.get().progressRepo.bump(subject, true)
                }
            }
        }
        _state.value = cur.copy(bonusClaimed = true)
        return bonus
    }

    fun isTodayCompleted(): Boolean {
        // 用 SharedPreferences 记录今天是否已完成
        return false // 简化版，暂不持久化
    }
}
