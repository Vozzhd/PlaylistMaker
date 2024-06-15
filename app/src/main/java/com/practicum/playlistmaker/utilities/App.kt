package com.practicum.playlistmaker.utilities

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.creator.Creator

const val SAVED_THEME_CONDITION = "saved_theme_condition"
const val KEY_FOR_TRACK = "TrackInExtra"
const val DEFAULT_TEXT = ""
const val SAVED_HISTORY_KEY = "saved search history"
const val DARK_THEME = "dark theme"


class App : Application() {

    var darkTheme: Boolean = false

    init {
        Creator.application = this
    }

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = Creator.getSharedPrefs()!!
        val statusOfDarkState = sharedPrefs.getBoolean(DARK_THEME, false)

        switchTheme(statusOfDarkState)
    }

fun switchTheme(darkThemeEnabled: Boolean) {
    darkTheme = darkThemeEnabled
    AppCompatDelegate.setDefaultNightMode(
        if (darkThemeEnabled) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
    )
}
}
