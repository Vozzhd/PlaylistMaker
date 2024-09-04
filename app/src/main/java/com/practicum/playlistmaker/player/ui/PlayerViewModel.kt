package com.practicum.playlistmaker.player.ui

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.mediaLibrary.favorite.domain.api.FavoriteTrackInteractor
import com.practicum.playlistmaker.player.domain.api.GetTrackUseCase
import com.practicum.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.PlayerState
import com.practicum.playlistmaker.player.ui.presenters.AddTrackToPlaylistRequestResult
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.Resource
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

    private lateinit var track: Track

    fun initPlayer(track: Track) {
        this.track = getTrackUseCase.execute(track)
        isInFavorite(track)
        mediaPlayer.preparePlayer(this.track.previewUrl)
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

    fun favoriteButtonClicked() {

        when (track.isFavorite) {
            true -> {
                viewModelScope.launch {
                    favoriteTrackInteractor.deleteFromFavorite(track)
                }
                track.isFavorite = false
            }

            false -> {
                viewModelScope.launch {
                    favoriteTrackInteractor.addToFavorite(track)
                }
                track.isFavorite = true
            }
        }
        isFavoriteMutableLiveData.postValue(track.isFavorite)
    }

    fun isInFavorite(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteTrackInteractor.getFavoriteTrackList().collect { ids ->
                isFavorite = ids.contains(track)
                isFavoriteMutableLiveData.postValue(isFavorite)
            }
        }
        this.track.isFavorite = track.isFavorite
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

//    private fun renderToast(addingStatus: AddTrackToPlaylistRequestResult): Resource {
//        when (addingStatus) {
//            is AddTrackToPlaylistRequestResult.Success -> {
//                bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
//                Toast.makeText(
//                    requireContext(Application),
//                    "${requireContext().getString(R.string.track_added)} ${"Name playlist good"}.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            is AddTrackToPlaylistRequestResult.Error -> {
//                Toast.makeText(
//                    requireContext(),
//                    "${requireContext().getString(R.string.track_added_yet)} ${"Name playlist bad"}.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
}
