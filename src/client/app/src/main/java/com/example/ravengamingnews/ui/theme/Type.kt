package com.example.ravengamingnews.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ravengamingnews.R


val BigNoodle = FontFamily(
    // Normal - Letters are straight up and down
    Font(R.font.big_noodle_titling),
    // Oblique - the letters are tilted (more like italic so giving it that style.
    Font(R.font.big_noodle_titling_oblique, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = BigNoodle,
        fontStyle = FontStyle.Italic,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
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
