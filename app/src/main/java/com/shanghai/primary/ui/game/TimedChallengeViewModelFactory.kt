package com.shanghai.primary.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shanghai.primary.data.model.Subject

class TimedChallengeViewModelFactory(
    private val subject: Subject,
    private val grade: Int = 1
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimedChallengeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimedChallengeViewModel(subject, grade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
