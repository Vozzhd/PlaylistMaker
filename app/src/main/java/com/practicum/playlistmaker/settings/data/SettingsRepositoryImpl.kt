package com.practicum.playlistmaker.settings.data

import android.app.Application
import android.content.SharedPreferences
import com.practicum.playlistmaker.settings.domain.model.ThemeSettings
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.utilities.App
import com.practicum.playlistmaker.utilities.DARK_THEME

class SettingsRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        val darkTheme = sharedPreferences.getBoolean(DARK_THEME, false)
        return ThemeSettings(darkTheme)
    }

    override fun updateThemeSettings(settings: ThemeSettings) {
        sharedPreferences.edit().putBoolean(DARK_THEME, settings.darkTheme).apply()
    }
}