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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanghai.primary.data.model.Subject

@Composable
fun WrongQuestionScreen(
    vm: WrongQuestionViewModel,
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
                    Text("<", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    "错题本",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.weight(1f))
                if (state.items.isNotEmpty()) {
                    Text(
                        "清空",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { vm.clearAll() }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (state.items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎉", fontSize = 64.sp)
                        Spacer(Modifier.height(12.dp))
                        Text("还没有错题哦！", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("继续加油！", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.items) { item ->
                        WrongQuestionItem(
                            item = item,
                            onDelete = { vm.deleteWrongQuestion(item.wrongQuestion.questionId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WrongQuestionItem(
    item: WrongQuestionWithQuestion,
    onDelete: () -> Unit
) {
    val subjectColor = when (item.wrongQuestion.subject) {
        Subject.CHINESE -> listOf(Color(0xFFFF9A8B), Color(0xFFFFAD42))
        Subject.MATH -> listOf(Color(0xFF4F8BF7), Color(0xFF8B5CF6))
        Subject.ENGLISH -> listOf(Color(0xFF7ED957), Color(0xFF38BDF8))
        Subject.GENERAL -> listOf(Color(0xFFFFB347), Color(0xFFFF6F61))
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            Modifier
                .background(
                    androidx.compose.ui.graphics.Brush.horizontalGradient(subjectColor)
                )
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    val prompt = item.question?.prompt ?: "题目已删除"
                    Text(
                        text = if (prompt.length > 30) prompt.take(30) + "…" else prompt,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${item.wrongQuestion.subject.title} · ${item.wrongQuestion.grade}年级",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        "错误次数：${item.wrongQuestion.wrongCount}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "删除",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
