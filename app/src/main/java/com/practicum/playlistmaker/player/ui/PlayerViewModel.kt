package com.practicum.playlistmaker.player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.player.domain.model.PlayerState
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    track: Track,
    private val mediaPlayer: MediaPlayerInteractorImpl
) : ViewModel() {
    init {
        mediaPlayer.preparePlayer(track.previewUrl)
    }

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val TIMER_DELAY_MILLS = 300L
    }

    private val playBackMutableLiveData = MutableLiveData<PlayerState>()

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    private val currentTimeMutableLiveData = MutableLiveData<String>()
    fun getCurrentTimeLiveData(): LiveData<String> = currentTimeMutableLiveData

    private val timeRunnable = Runnable {
        currentTimeMutableLiveData.postValue(getTimeFromRepository())
        updatingElapsedTrackTime()
    }

    fun updatingElapsedTrackTime() {
        playBackMutableLiveData.postValue(mediaPlayer.playerState())

        if (mediaPlayer.playerState() == PlayerState.PLAYING) {
            handler.postDelayed(timeRunnable, TIMER_DELAY_MILLS)
        }
    }

    private fun getTimeFromRepository(): String {
        return dateFormat.format(mediaPlayer.showCurrentPosition())
    }

    fun getPlayerState(): LiveData<PlayerState> = playBackMutableLiveData

    fun playBackControl() {
        mediaPlayer.playbackControl()
        playBackMutableLiveData.postValue(mediaPlayer.playerState())
    }

    fun removeUpdatingTimeCallbacks() {
        handler.removeCallbacks(timeRunnable)
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun release() {
        mediaPlayer.release()
    }
}