package com.shanghai.primary.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shanghai.primary.data.model.Subject

class GameViewModelFactory(
    private val subject: Subject,
    private val grade: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(subject, grade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
