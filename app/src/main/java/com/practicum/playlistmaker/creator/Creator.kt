package com.practicum.playlistmaker.creator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.search.data.api.TracksRepositoryImplementation
import com.practicum.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.search.data.api.TracksRepository
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.search.data.local.HistoryRepository
import com.practicum.playlistmaker.search.data.local.HistoryRepositoryImplementation
import com.practicum.playlistmaker.search.data.network.retrofit.RetrofitNetworkClient
import com.practicum.playlistmaker.search.domain.api.HistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.useCase.HistoryInteractorImplementation
import com.practicum.playlistmaker.search.domain.useCase.TracksInteractorImplementation
import com.practicum.playlistmaker.search.ui.SearchActivity


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

    fun provideTrackHistoryInteractor(context: Context): HistoryInteractor {
        return HistoryInteractorImplementation(provideTrackHistoryRepository(context))
    }

    private fun provideTrackHistoryRepository(context: Context): HistoryRepository {
        return HistoryRepositoryImplementation(
            context.getSharedPreferences(
                SearchActivity.SAVED_HISTORY_KEY,
                AppCompatActivity.MODE_PRIVATE
            )
        )
    }

}