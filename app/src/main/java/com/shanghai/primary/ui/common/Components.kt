package com.shanghai.primary.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanghai.primary.R
import com.shanghai.primary.data.model.Question
import com.shanghai.primary.sound.SoundManager

enum class ChoiceState { Idle, Selected, Correct, Wrong }

@Composable
fun ProgressIndicator(current: Int, total: Int, stars: Int, modifier: Modifier = Modifier) {
    val ratio = if (total == 0) 0f else current.toFloat() / total
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.label_question, current, total),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    stringResource(R.string.label_stars, stars),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth().height(16.dp)
        ) {
            Box(Modifier.fillMaxSize().background(Color.Transparent)) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(ratio.coerceIn(0f, 1f)).fillMaxSize()
                ) { }
            }
        }
    }
}

@Composable
fun OptionButton(
    label: String,
    selected: ChoiceState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        label = "press"
    )

    val (bg, fg) = when (selected) {
        ChoiceState.Idle     -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onSurface
        ChoiceState.Selected -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f) to Color.White
        ChoiceState.Correct  -> MaterialTheme.colorScheme.tertiary to Color.White
        ChoiceState.Wrong    -> MaterialTheme.colorScheme.error.copy(alpha = 0.85f) to Color.White
    }

    val shape = RoundedCornerShape(24.dp)

    Surface(
        color = bg,
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .scale(scale)
            .clickable(
                enabled = selected == ChoiceState.Idle,
                onClick = onClick,
                onPress = { pressed = true },
                onRelease = { pressed = false }
            )
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 选项字母
                val letter = when (label.firstOrNull()) {
                    in 'A'..'Z' -> label.first().toString()
                    else -> ""
                }
                if (letter.isNotEmpty()) {
                    Surface(
                        shape = CircleShape,
                        color = fg.copy(alpha = 0.15f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(letter, color = fg, fontSize = 18.sp)
                        }
                    }
                    Spacer(Modifier.size(12.dp))
                }
                Text(label, color = fg, fontSize = 20.sp, modifier = Modifier.weight(1f))
                // 状态图标
                when (selected) {
                    ChoiceState.Correct ->
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    ChoiceState.Wrong ->
                        Icon(Icons.Filled.Cancel, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    else -> { }
                }
            }
        }
    }
}

@Composable
fun FeedbackOverlay(visible: Boolean, correct: Boolean, onNext: () -> Unit, isLast: Boolean) {
    AnimatedVisibility(visible = visible, enter = fadeIn() + scaleIn(), exit = fadeOut()) {
        Box(
            Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.45f)),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(32.dp).fillMaxWidth()
            ) {
                Column(
                    Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(if (correct) R.string.feedback_correct else R.string.feedback_wrong),
                        fontSize = 28.sp,
                        color = if (correct) MaterialTheme.colorScheme.tertiary
                                else MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(20.dp))
                    if (correct) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        modifier = Modifier.size(120.dp).clickable { onNext() }
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(
                                stringResource(if (isLast) R.string.btn_finish else R.string.btn_next),
                                color = Color.White,
                                fontSize = 22.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GapH(dp: Int) = Spacer(Modifier.height(dp.dp))

fun shuffleOptions(question: Question): List<Pair<Int, String>> {
    val opts = listOfNotNull(
        question.optionA, question.optionB, question.optionC, question.optionD
    )
    return opts.mapIndexed { idx, v -> idx to v }.shuffled()
}
