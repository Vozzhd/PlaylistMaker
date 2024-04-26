package com.practicum.playlistmaker.data.repository

import android.media.MediaPlayer
import com.practicum.playlistmaker.domain.api.MediaPlayerRepository
import com.practicum.playlistmaker.domain.model.PlayerState

class MediaPlayerRepositoryImpl : MediaPlayerRepository {
    private val mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.STATE_DEFAULT

    override fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playerState = PlayerState.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = PlayerState.STATE_PREPARED
        }
    }

    override fun playbackControl() {
        when (playerState) {
            PlayerState.STATE_PLAYING -> {
                pause()
            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                play()
            }
            PlayerState.STATE_DEFAULT -> {
                //do nothing
            }
        }
    }

    override fun play() {
        mediaPlayer.start()
        playerState = PlayerState.STATE_PLAYING
    }

    override fun pause() {
        mediaPlayer.pause()
        playerState = PlayerState.STATE_PAUSED
    }

    override fun stop() {
        mediaPlayer.release()
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun showCurrentPosition(): Int {
       return mediaPlayer.currentPosition
    }

    override fun playerState(): PlayerState {
        return playerState
    }
}