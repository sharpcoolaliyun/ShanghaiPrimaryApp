package com.shanghai.primary.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shanghai.primary.data.model.Subject

class FlashCardViewModelFactory(
    private val subject: Subject,
    private val grade: Int = 1
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashCardViewModel(subject, grade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
