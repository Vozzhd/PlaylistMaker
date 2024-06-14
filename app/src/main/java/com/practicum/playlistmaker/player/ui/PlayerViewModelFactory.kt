package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl

class PlayerViewModelFactory (track : Track, mediaPlayer: MediaPlayerInteractorImpl) : ViewModelProvider.Factory {

    private val track by lazy (LazyThreadSafetyMode.NONE){ track }
    private val mediaPlayer by lazy (LazyThreadSafetyMode.NONE){ mediaPlayer }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return PlayerViewModel(track, mediaPlayer) as T
    }
}