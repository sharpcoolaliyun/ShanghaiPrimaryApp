package com.shanghai.primary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanghai.primary.App
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.WrongQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WrongQuestionState(
    val items: List<WrongQuestionWithQuestion> = emptyList(),
    val isLoading: Boolean = false
)

data class WrongQuestionWithQuestion(
    val wrongQuestion: WrongQuestion,
    val question: Question?
)

class WrongQuestionViewModel : ViewModel() {

    private val _state = MutableStateFlow(WrongQuestionState())
    val state: StateFlow<WrongQuestionState> = _state.asStateFlow()

    init {
        loadWrongQuestions()
    }

    fun loadWrongQuestions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val wrongList = App.get().wrongQuestionRepo.getWrongQuestions()
            val questionIds = wrongList.map { it.questionId }
            val questions = App.get().questionRepo.getByIds(questionIds)
            val items = wrongList.map { wq ->
                WrongQuestionWithQuestion(
                    wrongQuestion = wq,
                    question = questions.find { q -> q.id == wq.questionId }
                )
            }
            _state.value = WrongQuestionState(items = items, isLoading = false)
        }
    }

    fun deleteWrongQuestion(questionId: Int) {
        viewModelScope.launch {
            App.get().wrongQuestionRepo.deleteWrongQuestion(questionId)
            loadWrongQuestions()
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            val wrongList = App.get().wrongQuestionRepo.getWrongQuestions()
            wrongList.forEach {
                App.get().wrongQuestionRepo.deleteWrongQuestion(it.questionId)
            }
            loadWrongQuestions()
        }
    }
}
