package com.practicum.playlistmaker.settings.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.search.ui.SearchViewModel

class SettingsViewModelFactory (application: Application): ViewModelProvider.Factory {

    private val application by lazy (LazyThreadSafetyMode.NONE){ application }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return SettingsViewModel(application) as T
    }
}