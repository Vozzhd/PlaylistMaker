package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.player.data.TrackPresentation
import com.practicum.playlistmaker.practice.TrackModel

class PlayerViewModelFactory (track : TrackPresentation, trackUrl : String) : ViewModelProvider.Factory {

    private val track by lazy (LazyThreadSafetyMode.NONE){ track }
    private val trackUrl by lazy (LazyThreadSafetyMode.NONE){ trackUrl }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return PlayerViewModel(trackUrl, track, Creator.provideMediaPlayer()) as T
    }
}