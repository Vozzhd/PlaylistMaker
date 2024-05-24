package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.player.data.TrackPresentation
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.player.domain.model.PlayerState

class PlayerViewModel(
    trackUrl: String,
    track: TrackPresentation,
    private val mediaPlayer: MediaPlayerInteractorImpl
) : ViewModel() {

    init {
        mediaPlayer.preparePlayer(trackUrl)
    }


    companion object {
        fun getViewModelFactory(
            trackUrl: String,
            track: TrackPresentation,
            mediaPlayer: MediaPlayerInteractorImpl
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlayerViewModel(
                        trackUrl,
                        track,
                        mediaPlayer
                    ) as T
                }
            }
    }

    private val playBackLiveData = MutableLiveData<PlayerState> (mediaPlayer.playerState())
    fun getPlayerState () : LiveData<PlayerState> = playBackLiveData

    fun playBackControl () {
        mediaPlayer.playbackControl()
        playBackLiveData.postValue(mediaPlayer.playerState())
    }
}