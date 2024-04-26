package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.PlayerState

interface MediaPlayerRepository {
    fun preparePlayer(url: String)
    fun playbackControl()
    fun play()
    fun pause()
    fun stop()
    fun release()
    fun showCurrentPosition(): Int
    fun playerState(): PlayerState

}