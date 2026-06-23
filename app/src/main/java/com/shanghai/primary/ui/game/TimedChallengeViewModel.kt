package com.shanghai.primary.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanghai.primary.App
import com.shanghai.primary.data.model.GameType
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.Subject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TimedChallengeState(
    val timeLeft: Int = 60,  // 剩余时间（秒）
    val score: Int = 0,       // 当前分数
    val combo: Int = 0,       // 连击数
    val current: Question? = null,
    val selected: Int? = null,
    val isAnswered: Boolean = false,
    val finished: Boolean = false,
    val totalQuestions: Int = 0  // 回答的题目总数
)

class TimedChallengeViewModel(
    private val subject: Subject,
    private val grade: Int = 1
) : ViewModel() {

    companion object {
        const val TOTAL_TIME = 60  // 总时间60秒
        const val SCORE_PER_QUESTION = 10  // 每题基础分数
        const val COMBO_BONUS_THRESHOLD = 3  // 连击加成阈值
        const val COMBO_BONUS_SCORE = 5  // 连击额外分数
    }

    private val _state = MutableStateFlow(TimedChallengeState())
    val state: StateFlow<TimedChallengeState> = _state.asStateFlow()

    private var questions: List<Question> = emptyList()
    private var usedIndices: MutableSet<Int> = mutableSetOf()
    private var countdownJob: Job? = null

    init {
        loadQuestions()
        startCountdown()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            questions = App.get().questionRepo.nextRound(subject, grade, 50)
                .filter { it.gameType == GameType.QUIZ }
            loadNextQuestion()
        }
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (_state.value.timeLeft > 0 && !_state.value.finished) {
                delay(1000L)
                val newTime = _state.value.timeLeft - 1
                _state.value = _state.value.copy(timeLeft = newTime)
                if (newTime <= 0) {
                    // 时间到
                    _state.value = _state.value.copy(finished = true)
                }
            }
        }
    }

    private fun loadNextQuestion() {
        if (questions.isEmpty()) {
            // 如果没有题目了，重新加载
            loadQuestions()
            return
        }

        // 随机选择一个未使用的题目
        val availableIndices = questions.indices.filter { it !in usedIndices }
        val nextQuestion = if (availableIndices.isNotEmpty()) {
            val randomIndex = availableIndices.random()
            usedIndices.add(randomIndex)
            questions[randomIndex]
        } else {
            // 如果所有题目都用过了，重置并重新选择
            usedIndices.clear()
            val randomIndex = questions.indices.random()
            usedIndices.add(randomIndex)
            questions[randomIndex]
        }

        _state.value = _state.value.copy(
            current = nextQuestion,
            selected = null,
            isAnswered = false
        )
    }

    fun answer(selectedOriginalIndex: Int) {
        val cur = _state.value
        if (cur.isAnswered || cur.current == null || cur.finished) return

        val correct = selectedOriginalIndex == cur.current.answerIndex
        val newCombo = if (correct) cur.combo + 1 else 0

        // 计算分数
        var newScore = cur.score
        if (correct) {
            newScore += SCORE_PER_QUESTION
            // 连击加成：连续答对3题以上，每题额外+5分
            if (newCombo >= COMBO_BONUS_THRESHOLD) {
                newScore += COMBO_BONUS_SCORE
            }
        }

        // 记录进度
        viewModelScope.launch {
            App.get().progressRepo.bump(subject, correct)
            if (!correct && cur.current != null) {
                App.get().wrongQuestionRepo.recordWrong(
                    cur.current.id, subject, grade
                )
            }
        }

        _state.value = cur.copy(
            selected = selectedOriginalIndex,
            isAnswered = true,
            score = newScore,
            combo = newCombo,
            totalQuestions = cur.totalQuestions + 1
        )

        // 1.5秒后自动加载下一题
        viewModelScope.launch {
            delay(1500L)
            if (!_state.value.finished) {
                loadNextQuestion()
            }
        }
    }

    fun playAgain() {
        countdownJob?.cancel()
        usedIndices.clear()
        _state.value = TimedChallengeState()
        loadQuestions()
        startCountdown()
    }

    fun getStarCount(): Int {
        val score = _state.value.score
        return when {
            score >= 150 -> 3
            score >= 100 -> 2
            score >= 50 -> 1
            else -> 0
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
