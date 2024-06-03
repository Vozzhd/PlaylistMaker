package com.practicum.playlistmaker.creator

import android.content.Context
import com.practicum.playlistmaker.search.data.dto.TracksRepositoryImplementation
import com.practicum.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.search.data.network.retrofit.RetrofitNetworkClient
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.useCase.TracksInteractorImplementation


object Creator {

    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }
    private fun provideMediaPlayerRepository(): MediaPlayerRepositoryImpl {
        return MediaPlayerRepositoryImpl()
    }



    private fun provideTracksRepository(context: Context): TracksRepository {
        return TracksRepositoryImplementation(RetrofitNetworkClient(context))
    }

    fun provideTracksInteractor(context: Context): TracksInteractor {
        return TracksInteractorImplementation(provideTracksRepository(context))
    }


//    private fun provideGetTrackNetworkClient(context: Context): PlaylistReceivingNetworkClient {
//        return PlaylistReceivingRetrofitNetworkClient(context)
//    }


}