package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val FILE_WITH_SAVED_STATES = "saved_theme_condition"
const val THEME_STATE = "state_of_dark_theme"

class App : Application() {
    private lateinit var sharedPrefs: SharedPreferences
    var darkTheme : Boolean = false
    //По умолчанию на данный момент после установки у приложения изначально тема
    override fun onCreate() {
        super.onCreate()
        // darkTheme = initTheme()

        sharedPrefs = getSharedPreferences(FILE_WITH_SAVED_STATES, MODE_PRIVATE)
        val statusOfDarkState = sharedPrefs.getBoolean(THEME_STATE, darkTheme)
        switchTheme(statusOfDarkState)
    }
    //    fun initTheme() : Boolean {
    //        Получение темы от темы устройства если файла нет. Скорректирую после вебинара
    //    }
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
