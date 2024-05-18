package com.practicum.playlistmaker.practice

import android.app.Application
import com.practicum.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl

class MyApplication : Application() {
    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }

    fun provideMediaPlayerRepository(): MediaPlayerRepositoryImpl {
        return MediaPlayerRepositoryImpl()
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractor()
    }
}
