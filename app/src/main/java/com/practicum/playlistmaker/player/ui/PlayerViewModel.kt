package com.practicum.playlistmaker.player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        fun getViewModelFactory(
            track: Track,
            mediaPlayer: MediaPlayerInteractorImpl
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlayerViewModel(
                        track,
                        mediaPlayer
                    ) as T
                }
            }
    }

    private val playBackMutableLiveData = MutableLiveData<PlayerState>()

    ///Место для разработки

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
    } //Место для разработки

    fun getPlayerState(): LiveData<PlayerState> = playBackMutableLiveData // функция для наблюдения за состоянием из активити

    fun playBackControl() {
        mediaPlayer.playbackControl()
        playBackMutableLiveData.postValue(mediaPlayer.playerState())
    } // функция для управления кнопкой из активити

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