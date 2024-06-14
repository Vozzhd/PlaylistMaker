package com.practicum.playlistmaker.settings.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.search.ui.SearchViewModel
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SharingInteractor
import com.practicum.playlistmaker.settings.domain.model.ThemeSettings

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
            }
        }
    }

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
        themeSettingsLiveData.postValue(settingsInteractor.getThemeSettings())
    }
}