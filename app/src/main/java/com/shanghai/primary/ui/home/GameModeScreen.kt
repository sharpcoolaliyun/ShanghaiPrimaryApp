package com.shanghai.primary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanghai.primary.data.model.Subject
import com.shanghai.primary.data.model.GameType

data class GameModeConfig(
    val gameType: String,
    val title: String,
    val subtitle: String,
    val iconTint: Color,
    val colors: List<Color>
)

fun getGameModesForSubject(subject: Subject): List<GameModeConfig> {
    return when (subject) {
        Subject.CHINESE -> listOf(
            GameModeConfig(
                gameType = GameType.QUIZ,
                title = "答题闯关",
                subtitle = "选择题 · 看看你有多厉害",
                iconTint = Color.White,
                colors = listOf(Color(0xFFFF9A8B), Color(0xFFFFAD42))
            ),
            GameModeConfig(
                gameType = GameType.FLASHCARD,
                title = "识字闪卡",
                subtitle = "翻转卡片 · 认识更多汉字",
                iconTint = Color.White,
                colors = listOf(Color(0xFFE195AB), Color(0xFFF6A085)))
            ),
            GameModeConfig(
                gameType = GameType.DRAG_MATCH,
                title = "反义词配对",
                subtitle = "拖拽配对 · 大配小 多配少",
                iconTint = Color.White,
                colors = listOf(Color(0xFF7ED957), Color(0xFF38BDF8)))
        )
        Subject.MATH -> listOf(
            GameModeConfig(
                gameType = GameType.QUIZ,
                title = "口算闯关",
                subtitle = "加减乘除 · 看谁算得快",
                iconTint = Color.White,
                colors = listOf(Color(0xFF4F8BF7), Color(0xFF8B5CF6)))
            ),
            GameModeConfig(
                gameType = GameType.TIMED,
                title = "计时挑战",
                subtitle = "60秒限时 · 答对越多分越高",
                iconTint = Color.White,
                colors = listOf(Color(0xFFFF6B6B), Color(0xFFFFE66D)))
        )
        Subject.ENGLISH -> listOf(
            GameModeConfig(
                gameType = GameType.QUIZ,
                title = "单词答题",
                subtitle = "选择题 · 看看你认识多少单词",
                iconTint = Color.White,
                colors = listOf(Color(0xFF7ED957), Color(0xFF38BDF8)))
            ),
            GameModeConfig(
                gameType = GameType.FLASHCARD,
                title = "单词闪卡",
                subtitle = "翻转卡片 · 记住更多单词",
                iconTint = Color.White,
                colors = listOf(Color(0xFFE195AB), Color(0xFFF6A085)))
            ),
            GameModeConfig(
                gameType = GameType.WORD_SCRAMBLE,
                title = "拼字游戏",
                subtitle = "打乱字母 · 拼出正确单词",
                iconTint = Color.White,
                colors = listOf(Color(0xFFFFB347), Color(0xFFFF6F61)))
            ),
            GameModeConfig(
                gameType = GameType.DRAG_MATCH,
                title = "英汉配对",
                subtitle = "拖拽配对 · 英文配中文",
                iconTint = Color.White,
                colors = listOf(Color(0xFF4F8BF7), Color(0xFF8B5CF6)))
        )
        Subject.GENERAL -> listOf(
            GameModeConfig(
                gameType = GameType.QUIZ,
                title = "常识问答",
                subtitle = "生活 · 安全 · 自然",
                iconTint = Color.White,
                colors = listOf(Color(0xFFFFB347), Color(0xFFFF6F61)))
            ),
            GameModeConfig(
                gameType = GameType.DRAG_MATCH,
                title = "常识配对",
                subtitle = "拖拽配对 · 电话配号码",
                iconTint = Color.White,
                colors = listOf(Color(0xFF7ED957), Color(0xFF38BDF8)))
        )
    }
}

@Composable
fun GameModeScreen(
    subject: Subject,
    grade: Int = 1,
    onSelectGameMode: (String) -> Unit,
    onBack: () -> Unit
) {
    val modes = getGameModesForSubject(subject)
    val subjectName = when (subject) {
        Subject.CHINESE -> "语文"
        Subject.MATH -> "数学"
        Subject.ENGLISH -> "英语"
        Subject.GENERAL -> "常识"
    }

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
                    Text("<", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    "$subjectName（${grade}年级）- 选择游戏模式",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.height(20.dp))

            modes.forEachIndexed { index, mode ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { it * (index + 1) / 5 }
                    )
                ) {
                    GameModeCard(mode) { onSelectGameMode(mode.gameType) }
                    Spacer(Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
private fun GameModeCard(cfg: GameModeConfig, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by remember(pressed) { mutableStateOf(if (pressed) 0.96f else 1f) }

    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .scale(scale)
            .clickable(
                onClick = onClick,
                onPress = { pressed = true },
                onRelease = { pressed = false }
            )
    ) {
        Box(
            Modifier
                .background(Brush.horizontalGradient(cfg.colors))
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 游戏模式图标
                when (cfg.gameType) {
                    GameType.QUIZ ->
                        Icon(Icons.Filled.Quiz, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(48.dp))
                    GameType.FLASHCARD ->
                        Text("📖", fontSize = 42.sp)
                    GameType.DRAG_MATCH ->
                        Text("🤝", fontSize = 42.sp)
                    GameType.WORD_SCRAMBLE ->
                        Icon(Icons.Filled.Spellcheck, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(48.dp))
                    GameType.TIMED ->
                        Icon(Icons.Filled.Timer, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(48.dp))
                    else ->
                        Text("🎮", fontSize = 42.sp)
                }
                Spacer(Modifier.size(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        cfg.title,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        cfg.subtitle,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}
