package com.example.ravengamingnews.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.ravengamingnews.R

enum class CommonUiSize { Small, Medium, Large }

fun linkTextStyle(size: CommonUiSize) = when (size) {
    CommonUiSize.Small -> LinkTextSmall
    CommonUiSize.Medium -> LinkTextMedium
    CommonUiSize.Large -> LinkTextLarge
}

val BigNoodle = FontFamily(
    // Normal - Letters are straight up and down
    Font(R.font.big_noodle_titling),
    // Oblique - the letters are tilted (more like italic so giving it that style.
    Font(R.font.big_noodle_titling_oblique, style = FontStyle.Italic)
)

val LinkTextSmall = TextStyle(
    fontFamily = FontFamily.Default,
    textDecoration = TextDecoration.Underline,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

val LinkTextMedium = TextStyle(
    fontFamily = FontFamily.Default,
    textDecoration = TextDecoration.Underline,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val LinkTextLarge = TextStyle(
    fontFamily = FontFamily.Default,
    textDecoration = TextDecoration.Underline,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.5.sp
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = BigNoodle,
        fontStyle = FontStyle.Italic,
        fontSize = 30.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BigNoodle,
        fontStyle = FontStyle.Italic,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = BigNoodle,
        fontStyle = FontStyle.Italic,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontStyle = FontStyle.Italic,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
/* Other default text styles to override
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
*/
)
