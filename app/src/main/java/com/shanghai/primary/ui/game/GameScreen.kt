package com.shanghai.primary.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanghai.primary.R
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.sound.SoundManager
import com.shanghai.primary.ui.common.CelebrationOverlay
import com.shanghai.primary.ui.common.ChoiceState
import com.shanghai.primary.ui.common.FeedbackOverlay
import com.shanghai.primary.ui.common.OptionButton
import com.shanghai.primary.ui.common.ProgressIndicator
import com.shanghai.primary.ui.common.GapH
import com.shanghai.primary.ui.common.shuffleOptions

@Composable
fun GameScreen(
    vm: GameViewModel,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    var showCelebration by remember { mutableStateOf(false) }

    // 音效：答题后播放
    LaunchedEffect(state.isAnswered, state.selected) {
        if (state.isAnswered && state.selected >= 0) {
            val q = state.current
            if (q != null) {
                val correct = state.selected == q.answerIndex
                if (correct) {
                    SoundManager.playCorrect(context)
                    if ((state.score) > 0) {
                        SoundManager.playStar(context)
                    }
                } else {
                    SoundManager.playWrong(context)
                }
            }
        }
    }

    // 音效 + 庆祝：完成一轮
    LaunchedEffect(state.finished) {
        if (state.finished) {
            showCelebration = true
            SoundManager.playComplete(context)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(16.dp)
            ) {
                // 顶部栏
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(48.dp).clickable(onClick = onBack)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(Modifier.size(12.dp))
                    val title = when (state.current?.subject) {
                        com.shanghai.primary.data.model.Subject.CHINESE -> "语文 · 认字小达人"
                        com.shanghai.primary.data.model.Subject.MATH -> "数学 · 口算闯关"
                        com.shanghai.primary.data.model.Subject.ENGLISH -> "英语 · 单词卡片"
                        else -> "常识 · 小问答"
                    }
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(Modifier.height(16.dp))
                ProgressIndicator(
                    current = state.index + 1,
                    total = state.total.coerceAtLeast(1),
                    stars = state.score
                )

                Spacer(Modifier.height(24.dp))
                val q = state.current
                if (q != null) {
                    QuestionCard(q)
                }

                Spacer(Modifier.height(24.dp))

                val options = remember(q?.id) { q?.let { shuffleOptions(it) } ?: emptyList() }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    options.forEach { (origIdx, text) ->
                        val optIndex = options.indexOf(origIdx to text)
                        val stateOfOption = when {
                            !state.isAnswered ->
                                if (optIndex == state.selected) ChoiceState.Selected else ChoiceState.Idle
                            else -> {
                                if (origIdx == q?.answerIndex) ChoiceState.Correct
                                else if (optIndex == state.selected) ChoiceState.Wrong
                                else ChoiceState.Idle
                            }
                        }
                        OptionButton(label = text, selected = stateOfOption) {
                            if (!state.isAnswered) {
                                SoundManager.playClick(context)
                                vm.answer(origIdx)
                            }
                        }
                    }
                }

                Spacer(Modifier.fillMaxSize().weight(1f))
            }

            // 庆祝动画覆盖层
            CelebrationOverlay(
                active = showCelebration,
                onDone = { showCelebration = false }
            )
        }
    }
}

@Composable
private fun QuestionCard(q: Question) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            q.imageEmoji?.let {
                Text(it, fontSize = 56.sp)
                Spacer(Modifier.height(12.dp))
            }
            Text(
                q.prompt,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            q.hint?.let { hintText ->
                Spacer(Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "提示: $hintText",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FinishedDialog(
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val stars = (score.toFloat() / total.coerceAtLeast(1) * 5).toInt().coerceIn(0, 5)

    LaunchedEffect(Unit) {
        SoundManager.playComplete(context)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(32.dp).fillMaxWidth()
        ) {
            Column(
                Modifier.padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "闯关完成！",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                // 星星评级
                Row {
                    repeat(5) { i ->
                        Text(
                            if (i < stars) "★" else "☆",
                            fontSize = 36.sp,
                            color = if (i < stars) Color(0xFFFFD700) else Color.LightGray
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    "得分  $score / $total",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ResultButton("回到首页", MaterialTheme.colorScheme.primary, onHome)
                    ResultButton("再玩一次", MaterialTheme.colorScheme.tertiary, onPlayAgain)
                }
            }
        }
    }
}

@Composable
private fun ResultButton(text: String, color: Color, onClick: () -> Unit) {
    Surface(
        color = color,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.height(56.dp).clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 24.dp).fillMaxSize()
        ) {
            Text(text, color = Color.White, fontSize = 18.sp)
        }
    }
}
