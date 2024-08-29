package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.mediaLibrary.favorite.domain.api.FavoriteTrackInteractor
import com.practicum.playlistmaker.player.domain.api.GetTrackUseCase
import com.practicum.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.PlayerState
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    private val getTrackUseCase: GetTrackUseCase,
    private val mediaPlayer: MediaPlayerInteractor,
    private val favoriteTrackInteractor: FavoriteTrackInteractor,
    private val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {
    fun initPlayer(json: Track) {
        val track = getTrackUseCase.execute(json)
        mediaPlayer.preparePlayer(track.previewUrl)
        isInFavorite(track)
        saveTrackInBuffer(track)
    }

    private var isFavorite = false
    private var timerJob: Job? = null

    companion object {
        private const val TIMER_DELAY_MILLS = 300L
    }

    private val playBackMutableLiveData = MutableLiveData<PlayerState>()
    private val isFavoriteMutableLiveData = MutableLiveData<Boolean>()
    private val listWithPlaylists = MutableLiveData<List<Playlist>>()
    private val trackBuffer = MutableLiveData<Track>()
    val addTrackStatus = MutableLiveData<AddTrackStatus>()

    fun observePlayerState(): LiveData<PlayerState> = playBackMutableLiveData
    fun observeIsFavorite(): LiveData<Boolean> = isFavoriteMutableLiveData
    fun observeListWithPlaylists(): LiveData<List<Playlist>> = listWithPlaylists
    val trackInBufferLiveData: LiveData<Track> = trackBuffer
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    private val currentTimeMutableLiveData = MutableLiveData<String>()
    fun getCurrentTimeLiveData(): LiveData<String> = currentTimeMutableLiveData

    private fun getTimeFromRepository(): String {
        return dateFormat.format(mediaPlayer.showCurrentPosition())
    }

    fun playBackControl() {
        mediaPlayer.playbackControl()
        playBackMutableLiveData.postValue(mediaPlayer.playerState())
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun release() {
        mediaPlayer.release()
    }

    fun getCurrentTime() {
        timerJob = viewModelScope.launch {
            while (mediaPlayer.playerState() == PlayerState.PLAYING) {
                delay(TIMER_DELAY_MILLS)
                currentTimeMutableLiveData.postValue(getTimeFromRepository())
                playBackMutableLiveData.postValue(mediaPlayer.playerState())
            }
        }
    }

    fun favoriteButtonClicked(track: Track) {
        viewModelScope.launch {

            when (isFavorite) {
                true -> {
                    favoriteTrackInteractor.deleteFromFavorite(track)
                    track.isFavorite = false
                }

                false -> {
                    favoriteTrackInteractor.addToFavorite(track)
                    track.isFavorite = true
                }
            }
            isFavoriteMutableLiveData.postValue(track.isFavorite)
        }
    }

    private fun isInFavorite(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteTrackInteractor.getFavoriteTrackList()
                .collect { ids ->
                    isFavorite = ids.contains(track)
                    isFavoriteMutableLiveData.postValue(isFavorite)
                }
        }
    }

    private fun saveTrackInBuffer(track: Track) {
        trackBuffer.value = track
    }

    fun updateListOfPlaylists() {
        viewModelScope.launch {
            playlistManagerInteractor.getPlaylistsFromTable().collect() {
                listWithPlaylists.postValue(it)
            }
        }
    }

    fun addTrackToPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            if (trackBuffer.value != null && playlist.id != null) {
                val track = trackBuffer.value as Track
                addTrackStatus.postValue(AddTrackStatus(playlistManagerInteractor.addTrackToPlaylist(track, playlist.id) > 0, playlist.name
                    )
                )
            }
            updateList()
        }
    }

    fun updateList() {
        viewModelScope.launch {
            playlistManagerInteractor.getPlaylistsFromTable().collect() {
                listWithPlaylists.value = it
            }
        }
    }
}

class AddTrackStatus(var success: Boolean, var playlistName: String) {

}
