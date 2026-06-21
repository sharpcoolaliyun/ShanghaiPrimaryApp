package com.shanghai.primary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography

// 儿童友好配色 —— 明亮高对比
private val Primary = Color(0xFF4F8BF7)
private val Secondary = Color(0xFFFFB347)
private val Tertiary = Color(0xFF7ED957)
private val Warning = Color(0xFFFF6F61)

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD8E6FF),
    secondary = Secondary,
    onSecondary = Color(0xFF3A2C00),
    tertiary = Tertiary,
    background = Color(0xFFFFFCF2),
    onBackground = Color(0xFF101824),
    surface = Color.White,
    onSurface = Color(0xFF101824),
    error = Warning
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7AB0FF),
    secondary = Color(0xFFFFCA7A),
    tertiary = Color(0xFF9DE07F),
    background = Color(0xFF101824),
    onBackground = Color(0xFFEFF5FF),
    surface = Color(0xFF1B2638),
    onSurface = Color(0xFFEFF5FF)
)

private val KidTypography = Typography(
    headlineLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.ExtraBold, fontSize = 36.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 28.sp),
    titleLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 16.sp)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = KidTypography,
        content = content
    )
}
