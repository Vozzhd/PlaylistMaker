package com.practicum.playlistmaker.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SharingInteractor
import com.practicum.playlistmaker.settings.domain.model.ThemeSettings

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val shareLiveData = MutableLiveData<Unit>()
    private val themeSettings = MutableLiveData<ThemeSettings>()
    private val themeSettingsLiveData :LiveData<ThemeSettings> = themeSettings

    fun shareApp() = shareLiveData.postValue(sharingInteractor.shareApp())
    fun sendEmailToSupport() = sharingInteractor.openSupport()
    fun showUserAgreement() = sharingInteractor.openTerms()
    fun getThemeSettingsLiveData(): LiveData<ThemeSettings> = themeSettingsLiveData
    fun switchTheme(darkTheme: Boolean) {
        settingsInteractor.updateThemeSettings(ThemeSettings(darkTheme))
        themeSettings.value = settingsInteractor.getThemeSettings()
    }

    fun getThemeSettings() {
        themeSettings.value = settingsInteractor.getThemeSettings()
    }
}