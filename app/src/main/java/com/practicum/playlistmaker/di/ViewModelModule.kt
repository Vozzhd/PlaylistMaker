package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.main.ui.MainViewModel
import com.practicum.playlistmaker.mediaLibrary.ui.viewModel.FavoriteFragmentViewModel
import com.practicum.playlistmaker.player.ui.PlayerViewModel
import com.practicum.playlistmaker.playlistCreating.ui.model.PlaylistManagerViewModel
import com.practicum.playlistmaker.search.ui.SearchViewModel
import com.practicum.playlistmaker.settings.ui.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        PlayerViewModel(get(), get(),get())
    }
    viewModel {
        SearchViewModel(get(), get(),androidApplication())
    }
    viewModel {
        SettingsViewModel(get(), get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        FavoriteFragmentViewModel(get(),get())
    }
    viewModel {
        PlaylistManagerViewModel(get())
    }
}