package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val FILE_WITH_SAVED_STATES = "FILE_WITH_STATES"
const val THEME_STATE = "state_of_dark_theme"

class App : Application() {
    private lateinit var sharedPrefs: SharedPreferences
    var darkTheme = false
    override fun onCreate() {
        super.onCreate()
        sharedPrefs =
            getSharedPreferences(FILE_WITH_SAVED_STATES, MODE_PRIVATE) //Создали ссылку на файл
        val statusOfDarkState = sharedPrefs.getBoolean(THEME_STATE, darkTheme) //Вытащили оттуда статус по ключу
        switchTheme(statusOfDarkState)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled //закинули в darkTheme значение приходящее в метод
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                sharedPrefs.edit()
                    .putBoolean(THEME_STATE, true)
                    .apply()
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                sharedPrefs.edit()
                    .putBoolean(THEME_STATE, false)
                    .apply()
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}
