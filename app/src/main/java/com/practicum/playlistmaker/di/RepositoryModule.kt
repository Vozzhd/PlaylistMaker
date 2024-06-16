package com.practicum.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import com.google.gson.Gson
import com.practicum.playlistmaker.main.data.MainMenuRepositoryImpl
import com.practicum.playlistmaker.main.domain.api.MainMenuRepository
import com.practicum.playlistmaker.player.data.GetTrackRepositoryImpl
import com.practicum.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.player.domain.api.GetTrackRepository
import com.practicum.playlistmaker.player.domain.api.MediaPlayerRepository
import com.practicum.playlistmaker.search.data.api.TracksRepositoryImplementation
import com.practicum.playlistmaker.search.data.local.HistoryRepositoryImplementation
import com.practicum.playlistmaker.search.domain.api.HistoryRepository
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import com.practicum.playlistmaker.settings.data.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.data.SharingRepositoryImpl
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.settings.domain.api.SharingRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single {
        androidContext().getSharedPreferences("PlaylistMaker shared prefs", Context.MODE_PRIVATE)
    }

    single<MediaPlayerRepository> { MediaPlayerRepositoryImpl(get()) }
    factory <GetTrackRepository> {GetTrackRepositoryImpl()}
    single <TracksRepository> { TracksRepositoryImplementation(get()) }
    single <HistoryRepository> { HistoryRepositoryImplementation(get())}
    single <SharingRepository> { SharingRepositoryImpl(androidContext()) }
    single <SettingsRepository>{SettingsRepositoryImpl(get())}
    single <MainMenuRepository> {MainMenuRepositoryImpl(androidContext())}
    factory { MediaPlayer() }
    factory { Gson() }
}