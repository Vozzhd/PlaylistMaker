package com.practicum.playlistmaker.settings.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.settings.domain.model.ThemeSettings

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val shareLiveData = MutableLiveData<Unit>()
    private val themeSettingsLiveData = MutableLiveData<ThemeSettings>()

    private val sharingInteractor = Creator.provideSharingInteractor(getApplication())
    private val settingsInteractor = Creator.provideSettingInteractor(getApplication())
    fun shareApp() = shareLiveData.postValue(sharingInteractor.shareApp())
    fun sendEmailToSupport() = sharingInteractor.openSupport()
    fun showUserAgreement() = sharingInteractor.openTerms()
    fun getThemeSettingsLiveData(): LiveData<ThemeSettings> = themeSettingsLiveData
    fun switchTheme(darkTheme: Boolean) {
        settingsInteractor.updateThemeSettings(ThemeSettings(darkTheme))
        themeSettingsLiveData.value = settingsInteractor.getThemeSettings()
    }
}