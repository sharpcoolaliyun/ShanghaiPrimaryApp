package com.shanghai.primary.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shanghai.primary.data.model.Subject

class WordScrambleViewModelFactory(
    private val subject: Subject,
    private val grade: Int = 1
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordScrambleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordScrambleViewModel(subject, grade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
