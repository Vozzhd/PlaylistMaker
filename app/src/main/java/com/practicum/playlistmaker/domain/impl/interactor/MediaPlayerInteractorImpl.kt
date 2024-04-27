package com.practicum.playlistmaker.domain.impl.interactor

import com.practicum.playlistmaker.data.repository.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.domain.api.MediaPlayerInteractor
import com.practicum.playlistmaker.domain.model.PlayerState

class MediaPlayerInteractorImpl(
    private val mediaPlayer: MediaPlayerRepositoryImpl
) : MediaPlayerInteractor {
    override fun preparePlayer(url: String) {
        mediaPlayer.preparePlayer(url)
    }

    override fun playbackControl() {
        mediaPlayer.playbackControl()
    }

    override fun play() {
        mediaPlayer.play()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun showCurrentPosition(): Int {
        return mediaPlayer.showCurrentPosition()
    }

    override fun playerState(): PlayerState {
        return mediaPlayer.playerState()
    }
}