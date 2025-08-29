package com.example.odyssey.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    // Primary colors - Travel Blue
    primary = TravelBlue,
    onPrimary = Color.White,
    primaryContainer = TravelBlueLight,
    onPrimaryContainer = TravelBlueDark,

    // Secondary colors - Adventure Orange
    secondary = AdventureOrange,
    onSecondary = Color.White,
    secondaryContainer = AdventureOrangeLight,
    onSecondaryContainer = AdventureOrangeDark,

    // Tertiary colors - Nature Green
    tertiary = NatureGreen,
    onTertiary = Color.White,
    tertiaryContainer = NatureGreenLight,
    onTertiaryContainer = NatureGreenDark,

    // Background colors
    background = BackgroundLight,
    onBackground = OnBackgroundLight,

    // Surface colors
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray600,
    surfaceTint = TravelBlue,

    // Outline colors
    outline = Gray300,
    outlineVariant = Gray200,

    // Other colors
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFFFFEDED),
    onErrorContainer = Color(0xFF8B0000),

    inverseSurface = Gray800,
    inverseOnSurface = Gray100,
    inversePrimary = TravelBlueLight,

    scrim = Color.Black.copy(alpha = 0.32f)
)

private val DarkColorScheme = darkColorScheme(
    // Primary colors - Travel Blue
    primary = TravelBlueLight,
    onPrimary = TravelBlueDark,
    primaryContainer = TravelBlueDark,
    onPrimaryContainer = TravelBlueLight,

    // Secondary colors - Adventure Orange
    secondary = AdventureOrangeLight,
    onSecondary = AdventureOrangeDark,
    secondaryContainer = AdventureOrangeDark,
    onSecondaryContainer = AdventureOrangeLight,

    // Tertiary colors - Nature Green
    tertiary = NatureGreenLight,
    onTertiary = NatureGreenDark,
    tertiaryContainer = NatureGreenDark,
    onTertiaryContainer = NatureGreenLight,

    // Background colors
    background = BackgroundDark,
    onBackground = OnBackgroundDark,

    // Surface colors
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,
    surfaceTint = TravelBlueLight,

    // Outline colors
    outline = Gray600,
    outlineVariant = Gray700,

    // Other colors
    error = Color(0xFFFF6B6B),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    inverseSurface = Gray200,
    inverseOnSurface = Gray800,
    inversePrimary = TravelBlueDark,

    scrim = Color.Black.copy(alpha = 0.5f)
)

@Composable
fun OdysseyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to use our custom travel theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Extension for accessing custom travel colors
object TravelTheme {
    val colors = TravelColors()
}

data class TravelColors(
    val gradient: List<Color> = listOf(GradientStart, GradientEnd),
    val warning: Color = WarningAmber,
    val success: Color = SuccessGreen,
    val info: Color = TravelBlue,
    val neutral: Color = Gray500
)
