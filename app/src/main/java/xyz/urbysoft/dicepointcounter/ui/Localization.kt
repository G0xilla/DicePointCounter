package xyz.urbysoft.dicepointcounter.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import xyz.urbysoft.dicepointcounter.application.DefaultApplication
import java.util.Locale

/**
 * Provides a default [Locale] that should be used
 */
val LocalLocale = compositionLocalOf { Locale.US }

/**
 * Set [LocalLocale] to the value from [DefaultApplication.preferredLocale]. This assuming that
 * the application context can be converted to the [DefaultApplication].
 */
@Composable
fun Localization(
    content: @Composable () -> Unit
) {
    val application = LocalContext.current.applicationContext as DefaultApplication
    CompositionLocalProvider(LocalLocale provides application.preferredLocale) {
        content()
    }
}