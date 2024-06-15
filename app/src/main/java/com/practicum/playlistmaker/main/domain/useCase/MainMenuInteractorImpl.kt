package com.practicum.playlistmaker.main.domain.useCase
import com.practicum.playlistmaker.main.domain.api.MainMenuInteractor
import com.practicum.playlistmaker.main.domain.api.MainMenuRepository

class MainMenuInteractorImpl (private val mainMenuRepository: MainMenuRepository) : MainMenuInteractor {
    override fun startSearchActivity() {
        mainMenuRepository.startSearchActivity()
    }

    override fun startMediaLibraryActivity() {
        mainMenuRepository.startMediaLibraryActivity()
    }

    override fun startSettingsActivity() {
        mainMenuRepository.startSettingsActivity()
    }
}