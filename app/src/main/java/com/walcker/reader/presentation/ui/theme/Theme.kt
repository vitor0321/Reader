package com.walcker.reader.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Black,
    secondary = White,
    onSurface = Orange,
    onBackground = Light_Gray,
    onSecondary = Light_Blue,
    onPrimary = Gray
)

private val LightColorPalette = lightColors(
    primary = White,
    secondary = Black,
    onSurface = Orange,
    onBackground = Light_Gray,
    onSecondary = Light_Blue,
    onPrimary = Gray
)

@Composable
fun ReaderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}