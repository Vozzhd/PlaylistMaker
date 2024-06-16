package com.practicum.playlistmaker.utilities

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.di.dataModule
import com.practicum.playlistmaker.di.interactorModule
import com.practicum.playlistmaker.di.repositoryModule
import com.practicum.playlistmaker.di.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

const val KEY_FOR_TRACK = "TrackInExtra"
const val DEFAULT_TEXT = ""
const val SHARED_PREFS = "PlaylistMaker shared prefs"
const val DARK_THEME = "dark theme"


class App : Application() {

    var darkTheme: Boolean = false

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }

        val sharedPrefs by inject<SharedPreferences>()
        val statusOfDarkState = sharedPrefs.getBoolean(DARK_THEME, false)

        switchTheme(statusOfDarkState)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
