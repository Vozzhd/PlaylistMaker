package com.practicum.playlistmaker.player.domain.interactor

import com.practicum.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.practicum.playlistmaker.player.domain.api.MediaPlayerRepository
import com.practicum.playlistmaker.player.domain.model.PlayerState

class MediaPlayerInteractorImpl(
    private val mediaPlayer: MediaPlayerRepository
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