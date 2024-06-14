package com.practicum.playlistmaker.main.ui

import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.main.domain.api.MainMenuInteractor

class MainViewModel(private val mainMenuInteractor: MainMenuInteractor) : ViewModel() {
    fun startSearchActivity() {
        mainMenuInteractor.startSearchActivity()
    }

    fun startMediaLibraryActivity() {
        mainMenuInteractor.startMediaLibraryActivity()
    }

    fun startSettingsActivity() {
        mainMenuInteractor.startSettingsActivity()
    }
}