package xyz.urbysoft.dicepointcounter.application

import android.app.Application
import java.util.Locale

class DefaultApplication : Application() {

    /**
     * Default [Locale] which depends on the user's locale configuration and the locales supported by
     * this application.
     */
    val preferredLocale: Locale
        get() {
            val localeSize = resources.configuration.locales.size()
            for(i in 0 until localeSize) {
                val locale = resources.configuration.locales[i]
                when(locale.language) {
                    ("cs") -> return Locale("cs")
                    ("en") -> return Locale("en")
                }
            }

            return Locale.US
        }
}