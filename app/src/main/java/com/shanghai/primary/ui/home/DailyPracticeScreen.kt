package com.shanghai.primary.ui.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanghai.primary.ui.common.CelebrationOverlay
import com.shanghai.primary.ui.common.OptionButton

@Composable
fun DailyPracticeScreen(
    vm: DailyPracticeViewModel,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(20.dp)
        ) {
            // 顶部标题栏
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "返回", tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    "每日一练",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.height(16.dp))

            if (state.finished) {
                // 完成界面
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎉", fontSize = 64.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "今日一练完成！",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "得分：${state.score}/${state.total}",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(20.dp))
                        if (!state.bonusClaimed) {
                            Surface(
                                shape = RoundedCornerShape(28.dp),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(28.dp))
                                    .clickable { vm.claimBonus() }
                            ) {
                                Text(
                                    "领取奖励 ★+3",
                                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 14.dp),
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        } else {
                            Text(
                                "奖励已领取！",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    if (state.bonusClaimed) {
                        CelebrationOverlay()
                    }
                }
            } else if (state.current == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("加载中...", fontSize = 18.sp)
                }
            } else {
                // 答题界面
                val q = state.current!!
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 进度条
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(state.total) { idx ->
                            val color = if (idx < state.index) Color(0xFF4CAF50) else if (idx == state.index) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0)
                            Spacer(
                                Modifier
                                    .weight(1f)
                                    .height(8.dp)
                                    .background(color, shape = RoundedCornerShape(4.dp))
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                    Text(
                        "第 ${state.index + 1}/${state.total} 题",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                    if (q.imageEmoji != null) {
                        Text(q.imageEmoji, fontSize = 48.sp)
                        Spacer(Modifier.height(12.dp))
                    }
                    Text(
                        q.prompt,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(24.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        val options = listOf(q.optionA, q.optionB, q.optionC, q.optionD).filterNotNull()
                        options.forEachIndexed { idx, text ->
                            OptionButton(
                                text = text,
                                index = idx,
                                selectedIndex = state.selected,
                                correctIndex = if (state.isAnswered) q.answerIndex else null,
                                enabled = !state.isAnswered,
                                onClick = { vm.answer(idx) }
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))
                    if (state.isAnswered) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = RoundedCornerShape(28.dp),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(28.dp))
                                    .clickable { vm.next() }
                            ) {
                                Text(
                                    if (state.index >= state.total - 1) "查看结果" else "下一题",
                                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 14.dp),
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
