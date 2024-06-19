package com.practicum.playlistmaker.main.data

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker.main.domain.api.MainMenuRepository
import com.practicum.playlistmaker.mediaLibrary.ui.MediaLibraryFragment
import com.practicum.playlistmaker.search.ui.SearchFragment
import com.practicum.playlistmaker.settings.ui.SettingsActivity

class MainMenuRepositoryImpl (private val context: Context) : MainMenuRepository {
    override fun startSearchActivity() {
        context.startActivity(Intent(context,(SearchFragment::class.java)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun startMediaLibraryActivity() {
        context.startActivity(Intent(context,(MediaLibraryFragment::class.java)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun startSettingsActivity() {
        context.startActivity(Intent(context,(SettingsActivity::class.java)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })

    }
}