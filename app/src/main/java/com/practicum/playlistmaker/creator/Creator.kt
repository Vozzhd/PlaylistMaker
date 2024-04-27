package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.repository.GetTrackNetworkClient
import com.practicum.playlistmaker.data.network.GetTrackRetrofitNetworkClient
import com.practicum.playlistmaker.data.repository.TrackRepositoryImpl
import com.practicum.playlistmaker.data.repository.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.domain.impl.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.domain.impl.useCase.GetTrackListFromNetworkUseCase


object Creator {

    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }

    private fun provideMediaPlayerRepository(): MediaPlayerRepositoryImpl {
        return MediaPlayerRepositoryImpl()
    }

    fun provideGetTrackListUseCase(): GetTrackListFromNetworkUseCase {
        return GetTrackListFromNetworkUseCase(provideGetTrackRepository())
    }

    private fun provideGetTrackNetworkClient(): GetTrackNetworkClient {
        return GetTrackRetrofitNetworkClient()
    }

    private fun provideGetTrackRepository(): com.practicum.playlistmaker.domain.api.GetTrackListUseCase {
        return TrackRepositoryImpl(provideGetTrackNetworkClient())
    }
}