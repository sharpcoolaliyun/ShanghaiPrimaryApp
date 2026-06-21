package com.shanghai.primary.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanghai.primary.App
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameState(
    val index: Int = 0,
    val total: Int = 0,
    val current: Question? = null,
    val selected: Int? = null,
    val isAnswered: Boolean = false,
    val score: Int = 0,
    val finished: Boolean = false
)

class GameViewModel(
    private val subject: Subject,
    private val grade: Int = 1,
    private val roundSize: Int = 10
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var questions: List<Question> = emptyList()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            questions = App.get().questionRepo.nextRound(subject, grade, roundSize)
            val first = questions.firstOrNull()
            _state.value = GameState(
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
            App.get().progressRepo.bump(subject, correct)
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
            _state.value = GameState(
                index = nextIdx,
                total = questions.size,
                current = questions[nextIdx],
                score = cur.score
            )
        }
    }

    fun playAgain() {
        loadQuestions()
    }
}
