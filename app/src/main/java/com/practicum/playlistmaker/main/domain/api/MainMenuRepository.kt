package com.practicum.playlistmaker.main.domain.api

interface MainMenuRepository {
    fun startSearchActivity()
    fun startMediaLibraryActivity()
    fun startSettingsActivity()
}