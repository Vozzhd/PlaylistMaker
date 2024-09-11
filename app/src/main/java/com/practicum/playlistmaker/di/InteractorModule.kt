package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.main.domain.api.MainMenuInteractor
import com.practicum.playlistmaker.main.domain.useCase.MainMenuInteractorImpl
import com.practicum.playlistmaker.mediaLibrary.favorite.domain.api.FavoriteTrackInteractor
import com.practicum.playlistmaker.mediaLibrary.favorite.domain.impl.FavoriteTrackInteractorImpl
import com.practicum.playlistmaker.player.domain.api.GetTrackUseCase
import com.practicum.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.practicum.playlistmaker.player.domain.interactor.GetTrackUseCaseImpl
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.playlistManage.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistManage.domain.impl.PlaylistManagerInteractorImplementation
import com.practicum.playlistmaker.search.domain.api.HistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.useCase.HistoryInteractorImplementation
import com.practicum.playlistmaker.search.domain.useCase.TracksInteractorImplementation
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SharingInteractor
import com.practicum.playlistmaker.settings.domain.useCase.SettingsInteractorImpl
import com.practicum.playlistmaker.settings.domain.useCase.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MediaPlayerInteractor> { MediaPlayerInteractorImpl(get()) }
    single<GetTrackUseCase> { GetTrackUseCaseImpl(get()) }
    single<TracksInteractor> { TracksInteractorImplementation(get()) }
    single<HistoryInteractor> { HistoryInteractorImplementation(get()) }
    single<SharingInteractor> { SharingInteractorImpl(get(), get()) }
    single<SettingsInteractor> { SettingsInteractorImpl(get()) }
    single<MainMenuInteractor> { MainMenuInteractorImpl(get()) }
    single<FavoriteTrackInteractor> { FavoriteTrackInteractorImpl(get()) }
    single<PlaylistManagerInteractor> { PlaylistManagerInteractorImplementation(get()) }
}