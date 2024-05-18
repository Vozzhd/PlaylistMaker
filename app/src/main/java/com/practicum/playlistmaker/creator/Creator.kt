package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.search.data.network.TrackGettingNetworkClient
import com.practicum.playlistmaker.search.data.network.retrofit.TrackGettingRetrofitNetworkClient
import com.practicum.playlistmaker.search.data.dto.TrackRepositoryImplGetting
import com.practicum.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.TrackGettingListUseCase
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.search.domain.useCase.TrackListFromNetworkUseCase


object Creator {

    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }

    private fun provideMediaPlayerRepository(): MediaPlayerRepositoryImpl {
        return MediaPlayerRepositoryImpl()
    }

    fun provideGetTrackListUseCase(): TrackListFromNetworkUseCase {
        return TrackListFromNetworkUseCase(provideGetTrackRepository())
    }

    private fun provideGetTrackNetworkClient(): TrackGettingNetworkClient {
        return TrackGettingRetrofitNetworkClient()
    }

    private fun provideGetTrackRepository(): TrackGettingListUseCase {
        return TrackRepositoryImplGetting(provideGetTrackNetworkClient())
    }
}