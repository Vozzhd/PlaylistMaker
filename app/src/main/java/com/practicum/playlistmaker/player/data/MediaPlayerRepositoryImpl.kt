package com.practicum.playlistmaker.player.data

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.domain.api.MediaPlayerRepository
import com.practicum.playlistmaker.player.domain.model.PlayerState

class MediaPlayerRepositoryImpl(private var mediaPlayer: MediaPlayer) : MediaPlayerRepository {

    private var playerState = PlayerState.DEFAULT
    override fun preparePlayer(url: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { playerState = PlayerState.PREPARED }
        mediaPlayer.setOnCompletionListener { playerState = PlayerState.COMPLETED }
    }

override fun playbackControl() {
    when (playerState) {
        PlayerState.PLAYING -> {
            pause()
        }

        PlayerState.PREPARED, PlayerState.PAUSED -> {
            play()
        }

        PlayerState.DEFAULT -> {
            //do nothing
        }

        PlayerState.COMPLETED -> {
            playerState = PlayerState.PREPARED
        }
    }
}

override fun play() {
    mediaPlayer.start()
    playerState = PlayerState.PLAYING
}

override fun pause() {
    mediaPlayer.pause()
    playerState = PlayerState.PAUSED
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