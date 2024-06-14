package com.practicum.playlistmaker.main.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.creator.Creator

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val mainMenuInteractor by lazy {
        Creator.provideMainMenuInteractor(context)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mainMenuInteractor) as T
    }
}