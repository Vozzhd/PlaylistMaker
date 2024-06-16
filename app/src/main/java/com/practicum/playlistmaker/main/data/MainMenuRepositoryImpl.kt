package com.practicum.playlistmaker.main.data

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker.main.domain.api.MainMenuRepository
import com.practicum.playlistmaker.mediaLibrary.ui.MediaLibraryActivity
import com.practicum.playlistmaker.search.ui.SearchActivity
import com.practicum.playlistmaker.settings.ui.SettingsActivity

class MainMenuRepositoryImpl (private val context: Context) : MainMenuRepository {
    override fun startSearchActivity() {
        context.startActivity(Intent(context,(SearchActivity::class.java)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun startMediaLibraryActivity() {
        context.startActivity(Intent(context,(MediaLibraryActivity::class.java)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun startSettingsActivity() {
        context.startActivity(Intent(context,(SettingsActivity::class.java)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })

    }
}