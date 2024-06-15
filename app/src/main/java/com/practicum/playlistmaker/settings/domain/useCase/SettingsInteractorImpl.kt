package com.practicum.playlistmaker.settings.domain.useCase

import com.practicum.playlistmaker.settings.domain.model.ThemeSettings
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository

class SettingsInteractorImpl (private val settingsRepository: SettingsRepository) : SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
       return settingsRepository.getThemeSettings()
    }

    override fun updateThemeSettings(settings: ThemeSettings) {
        settingsRepository.updateThemeSettings(settings)
    }
}