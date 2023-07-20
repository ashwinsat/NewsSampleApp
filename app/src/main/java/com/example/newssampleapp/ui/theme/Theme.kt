package com.example.newssampleapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//private val DarkColorScheme = darkColorScheme(
//    primary = Color(0xFF0288D1),
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

/* Other default colors to override*/
//--ion-color-primary: #3880ff;
//--ion-color-primary-rgb: 56,128,255;
//--ion-color-primary-contrast: #ffffff;
//--ion-color-primary-contrast-rgb: 255,255,255;
//--ion-color-primary-shade: #3171e0;
//--ion-color-primary-tint: #4c8dff;
//
//--ion-color-secondary: #5260ff;
//--ion-color-secondary-rgb: 82,96,255;
//--ion-color-secondary-contrast: #ffffff;
//--ion-color-secondary-contrast-rgb: 255,255,255;
//--ion-color-secondary-shade: #4854e0;
//--ion-color-secondary-tint: #6370ff;
//
//--ion-color-tertiary: #5260ff;
//--ion-color-tertiary-rgb: 82,96,255;
//--ion-color-tertiary-contrast: #ffffff;
//--ion-color-tertiary-contrast-rgb: 255,255,255;
//--ion-color-tertiary-shade: #4854e0;
//--ion-color-tertiary-tint: #6370ff;

private val DarkColorScheme = lightColorScheme(
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFf4f5f8),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF212121),
    onTertiary = Color(0xFF757575),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    primary = Color(0xFF3880ff),
    secondary = Color(0xFF009688),
    tertiary = Color(0xFF5260ff)
)

private val LightColorScheme = lightColorScheme(
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color(0xFFFFFFFF),
//    onSecondary = Color(0xFF212121),
//    onTertiary = Color(0xFF757575),
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    primary = Color(0xFF03A9F4),
//    secondary = Color(0xFF009688),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFf4f5f8),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF212121),
    onTertiary = Color(0xFF757575),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    primary = Color(0xFF3880ff),
    secondary = Color(0xFF009688),
    tertiary = Color(0xFF5260ff)
)

@Composable
fun NewsSampleAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}