package com.shanghai.primary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanghai.primary.data.model.Subject

data class SubjectCardConfig(
    val subject: Subject,
    val title: String,
    val subtitle: String,
    val iconTint: Color,
    val colors: List<Color>,
    val stars: Int,
    val answered: Int
)

@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onSelectSubject: (Subject) -> Unit,
    onOpenWrongQuestions: () -> Unit,
    onOpenDailyPractice: () -> Unit
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
                .padding(horizontal = 20.dp)
        ) {
            // 顶部欢迎卡片
            Surface(
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                Box(
                    Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            )
                        )
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            "你好，小朋友！",
                            fontSize = 28.sp,
                            color = Color.White
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "今天来玩一玩，答对题目攒小星星",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(Modifier.height(12.dp))
                        val totalStars = state.starsBySubject.values.sum()
                        Text(
                            "我的小星星：$totalStars",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // 年级选择和错题本
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GradeChip(
                    grade = 1,
                    selected = state.selectedGrade == 1,
                    onClick = { vm.selectGrade(1) }
                )
                Spacer(Modifier.size(10.dp))
                GradeChip(
                    grade = 2,
                    selected = state.selectedGrade == 2,
                    onClick = { vm.selectGrade(2) }
                )
                Spacer(Modifier.weight(1f))
                // 错题本入口
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onOpenWrongQuestions() }
                ) {
                    Text(
                        "📕 错题本",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        fontSize = 14.sp
                    )
                }
                Spacer(Modifier.size(8.dp))
                // 每日一练入口
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onOpenDailyPractice() }
                ) {
                    Text(
                        "⭐ 每日一练",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "选择一个科目开始游戏",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(12.dp))

            val cards = listOf(
                SubjectCardConfig(
                    subject = Subject.CHINESE,
                    title = "认字小达人",
                    subtitle = "拼音 · 识字 · 古诗",
                    iconTint = Color(0xFFFFF3E0),
                    colors = listOf(Color(0xFFFF9A8B), Color(0xFFFFAD42)),
                    stars = state.starsBySubject[Subject.CHINESE] ?: 0,
                    answered = state.answeredBySubject[Subject.CHINESE] ?: 0
                ),
                SubjectCardConfig(
                    subject = Subject.MATH,
                    title = "口算闯关",
                    subtitle = "10/20以内加减 · 乘法入门",
                    iconTint = Color(0xFFE8EAF6),
                    colors = listOf(Color(0xFF4F8BF7), Color(0xFF8B5CF6)),
                    stars = state.starsBySubject[Subject.MATH] ?: 0,
                    answered = state.answeredBySubject[Subject.MATH] ?: 0
                ),
                SubjectCardConfig(
                    subject = Subject.ENGLISH,
                    title = "单词卡片",
                    subtitle = "看图选词 · 常用单词",
                    iconTint = Color(0xFFE0F7FA),
                    colors = listOf(Color(0xFF7ED957), Color(0xFF38BDF8)),
                    stars = state.starsBySubject[Subject.ENGLISH] ?: 0,
                    answered = state.answeredBySubject[Subject.ENGLISH] ?: 0
                ),
                SubjectCardConfig(
                    subject = Subject.GENERAL,
                    title = "常识问答",
                    subtitle = "生活 · 安全 · 自然",
                    iconTint = Color(0xFFFFF3E0),
                    colors = listOf(Color(0xFFFFB347), Color(0xFFFF6F61)),
                    stars = state.starsBySubject[Subject.GENERAL] ?: 0,
                    answered = state.answeredBySubject[Subject.GENERAL] ?: 0
                )
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                itemsIndexed(cards) { index, card ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it * (index + 1) / 3 }),
                        exit = fadeOut()
                    ) {
                        SubjectCard(card) { onSelectSubject(card.subject) }
                    }
                }
            }
        }
    }
}

@Composable
private fun GradeChip(
    grade: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Text(
            "${grade}年级",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun SubjectCard(cfg: SubjectCardConfig, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            Modifier
                .background(Brush.horizontalGradient(cfg.colors))
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 科目图标
                when (cfg.subject) {
                    Subject.CHINESE ->
                        Icon(Icons.Filled.AutoStories, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(52.dp))
                    Subject.MATH ->
                        Icon(Icons.Filled.Calculate, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(52.dp))
                    Subject.ENGLISH ->
                        Icon(Icons.Filled.Translate, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(52.dp))
                    Subject.GENERAL ->
                        Icon(Icons.Filled.Lightbulb, contentDescription = null, tint = cfg.iconTint, modifier = Modifier.size(52.dp))
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
                Column(horizontalAlignment = Alignment.End) {
                    Text("★ ${cfg.stars}", fontSize = 18.sp, color = Color.White)
                    Spacer(Modifier.height(4.dp))
                    Text("完成: ${cfg.answered}", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }
        }
    }
}
