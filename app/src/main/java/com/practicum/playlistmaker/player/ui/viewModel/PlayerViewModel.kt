package com.practicum.playlistmaker.player.ui.viewModel

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
import com.practicum.playlistmaker.utilities.Result
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

    fun initPlayer(track: Track) {
        isInFavorite(getTrackUseCase.execute(track))
        mediaPlayer.preparePlayer(track.previewUrl)
    }

    private var isFavorite = false
    private var timerJob: Job? = null

    companion object {
        private const val TIMER_DELAY_MILLS = 300L
    }

    private val playBackMutableLiveData = MutableLiveData<PlayerState>()
    private val isFavoriteMutableLiveData = MutableLiveData<Boolean>()
    private val listWithPlaylists = MutableLiveData<List<Playlist>>()
    private val addTrackStatus = MutableLiveData<Result>()
    private val currentTimeMutableLiveData = MutableLiveData<String>()

    fun observePlayerState(): LiveData<PlayerState> = playBackMutableLiveData
    fun observeIsFavorite(): LiveData<Boolean> = isFavoriteMutableLiveData
    fun observeListWithPlaylists(): LiveData<List<Playlist>> = listWithPlaylists
    fun observeCurrentTimeLiveData(): LiveData<String> = currentTimeMutableLiveData
    fun observeAddTrackStatus(): MutableLiveData<Result> = addTrackStatus

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

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

        when (isFavorite) {
            true -> {
                viewModelScope.launch {
                    favoriteTrackInteractor.deleteFromFavorite(track)
                }
                isFavorite = false
            }

            false -> {
                viewModelScope.launch {
                    favoriteTrackInteractor.addToFavorite(track)
                }
                isFavorite = true
            }
        }
        isFavoriteMutableLiveData.postValue(isFavorite)
    }

    fun isInFavorite(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteTrackInteractor.getFavoriteTrackList().collect { ids ->
                isFavorite = ids.contains(track)
                isFavoriteMutableLiveData.postValue(isFavorite)
            }
        }
        track.isFavorite = isFavorite
    }


    fun addRequestTrackToPlaylist(track: Track, playlist: Playlist) {
        if (playlist.listOfTrackIDs.contains(track.trackId)) {
            addTrackStatus.postValue(Result(false,playlist.name))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                playlistManagerInteractor.addTrackToPlaylist(track, playlist)
                addTrackStatus.postValue(Result(true,playlist.name))
                updateList()
            }
        }
    }

    fun updateList() {
        viewModelScope.launch {
            playlistManagerInteractor.getPlaylistsFromTable().collect() {
                listWithPlaylists.postValue(it)
            }
        }
    }
}
