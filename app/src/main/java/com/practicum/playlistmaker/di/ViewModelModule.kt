package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.main.ui.MainViewModel
import com.practicum.playlistmaker.mediaLibrary.favorite.ui.viewModel.FavoriteFragmentViewModel
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel.PlaylistViewModel
import com.practicum.playlistmaker.mediaLibrary.playlists.ui.viewModel.PlaylistsFragmentViewModel
import com.practicum.playlistmaker.player.ui.viewModel.PlayerViewModel
import com.practicum.playlistmaker.playlistManage.ui.model.PlaylistManagerViewModel
import com.practicum.playlistmaker.search.ui.SearchViewModel
import com.practicum.playlistmaker.settings.ui.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        PlayerViewModel(get(), get(), get(),get())
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
        FavoriteFragmentViewModel(get())
    }
    viewModel {
        PlaylistManagerViewModel(get())
    }
    viewModel {
        PlaylistsFragmentViewModel(get())
    }
    viewModel {
        PlaylistViewModel(get())
    }
}