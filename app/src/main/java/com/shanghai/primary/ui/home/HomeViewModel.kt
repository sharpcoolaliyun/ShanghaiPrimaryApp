package com.shanghai.primary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanghai.primary.App
import com.shanghai.primary.data.model.Progress
import com.shanghai.primary.data.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class HomeState(
    val starsBySubject: Map<Subject, Int> = emptyMap(),
    val answeredBySubject: Map<Subject, Int> = emptyMap()
)

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            App.get().progressRepo.observeAll().collectLatest { list ->
                val stars = list.associate { it.subject to it.stars }
                val answered = list.associate { it.subject to it.totalAnswered }
                _state.value = HomeState(stars, answered)
            }
        }
    }
}
