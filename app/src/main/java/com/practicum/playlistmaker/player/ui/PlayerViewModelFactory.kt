package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl

class PlayerViewModelFactory (track : Track, mediaPlayerInteractorImpl: MediaPlayerInteractorImpl) : ViewModelProvider.Factory {

    private val track by lazy (LazyThreadSafetyMode.NONE){ track }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return PlayerViewModel(track, Creator.provideMediaPlayer()) as T
    }
}