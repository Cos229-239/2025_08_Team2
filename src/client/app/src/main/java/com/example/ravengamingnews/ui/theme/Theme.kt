package com.example.ravengamingnews.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//)

/**
 * This is the main "Patch Raven Color Scheme"
 * no dark/light modes.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    primaryContainer = DarkPurple,
    onPrimary = Color.White,
    onPrimaryContainer = Gray,
    onSurface = Gray,
    onBackground = Gray,
    secondary = Gray,
    tertiary = Pink80,
    background = DarkPurple,
    error = Color.Red,
)

@Composable
fun RavenGamingNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
//        !darkTheme -> lightColorScheme
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
