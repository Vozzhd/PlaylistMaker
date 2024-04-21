package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.repository.GetTrackNetworkClient
import com.practicum.playlistmaker.data.network.GetTrackRetrofitNetworkClient
import com.practicum.playlistmaker.data.repository.GetTrackRepositoryImpl
import com.practicum.playlistmaker.domain.repository.GetTrackRepository
import com.practicum.playlistmaker.domain.useCase.GetTrackListUseCase


object Creator {

    fun provideGetTrackListUseCase(): GetTrackListUseCase {
        return GetTrackListUseCase(provideGetTrackRepository())
    }

    private fun provideGetTrackNetworkClient(): GetTrackNetworkClient {
        return GetTrackRetrofitNetworkClient()
    }

    private fun provideGetTrackRepository(): GetTrackRepository {
        return GetTrackRepositoryImpl(provideGetTrackNetworkClient())
    }
}