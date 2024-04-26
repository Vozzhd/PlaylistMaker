package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.repository.GetTrackNetworkClient
import com.practicum.playlistmaker.data.network.GetTrackRetrofitNetworkClient
import com.practicum.playlistmaker.data.repository.GetTrackRepositoryImpl
import com.practicum.playlistmaker.data.repository.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.domain.api.GetTrackListApi
import com.practicum.playlistmaker.domain.api.MediaPlayerInteractor
import com.practicum.playlistmaker.domain.api.MediaPlayerRepository
import com.practicum.playlistmaker.domain.impl.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.domain.impl.useCase.GetTrackListUseCase


object Creator {

    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }

    private fun provideMediaPlayerRepository(): MediaPlayerRepositoryImpl {
        return MediaPlayerRepositoryImpl()
    }

    fun provideGetTrackListUseCase(): GetTrackListUseCase {
        return GetTrackListUseCase(provideGetTrackRepository())
    }

    private fun provideGetTrackNetworkClient(): GetTrackNetworkClient {
        return GetTrackRetrofitNetworkClient()
    }

    private fun provideGetTrackRepository(): GetTrackListApi {
        return GetTrackRepositoryImpl(provideGetTrackNetworkClient())
    }
}